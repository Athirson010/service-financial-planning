package io.github.athirson010.financialPlanning.service;


import io.github.athirson010.financialPlanning.domain.AbstractModel;
import io.github.athirson010.financialPlanning.exception.NaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.security.InvalidParameterException;
import java.util.List;

public abstract class AbstractService<Model extends AbstractModel, Repository extends MongoRepository<Model, String>> {
    protected Repository repository;
    @Autowired
    protected MongoTemplate mongoTemplate;
    protected Class<Model> beanClass;
    protected String CONTEXTO;
    @Value("${findall.max.results}")
    private Integer findAllMaxResults;
    private final String ACCENT_STRINGS = "àáâãäåßòóôõöøèéêëðçÐìíîïùúûüñšÿýž";
    private final String NO_ACCENT_STRINGS = "aaaaaabooooooeeeeecdiiiiuuuunsyyz";
    private Model modelGenerico;

    public AbstractService(Class<Model> beanClass, Repository repository) {
        this.beanClass = beanClass;
        this.repository = repository;
        this.CONTEXTO = buscarContexto(beanClass.getName());
    }

    public List<Model> saveAll(List<Model> models) {
        return repository.saveAll(models);
    }

    public List<Model> findAll() {
        return findAll(null, null);
    }

    public List<Model> findAll(Integer page, Integer size) {
        if (page == null || size == null) {
            return repository.findAll(PageRequest.of(0, findAllMaxResults)).getContent();
        } else {
            return repository.findAll(PageRequest.of(page, size)).getContent();
        }
    }
    public Page<Model> findAllPage(Integer page, Integer size) {
        if (page == null || size == null) {
            return new PageImpl<>(repository.findAll());
        } else {
            return repository.findAll(PageRequest.of(page, size));
        }
    }

    public Model save(Model model) {
        return repository.save(model);
    }

    public Model update(String id, Model model) {
        this.findById(id);
        model.setId(id);
        modelGenerico = save(model);
        return modelGenerico;
    }

    public void deleteById(String id) {
        findById(id);
        repository.deleteById(id);
    }

    public Model findById(String id) {
        return repository.findById(id).orElseThrow(() -> new NaoEncontradoException(CONTEXTO));
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    protected long count(Query query) {
        return mongoTemplate.count(query, beanClass);
    }

    protected long countForHasMorePages(Integer page, Integer size, Query query) {
        return countForHasMorePages(page, size, query, 4);
    }

    protected long countForHasMorePages(Integer page, Integer size, Query query, int pagesAhead) {
        if (pagesAhead < 0) {
            throw new InvalidParameterException("pagesAhead must be equal or over 1");
        }
        int pagesToAdd = pagesAhead + 1;
        long countNextPage = mongoTemplate.find(query.with(PageRequest.of(page + pagesToAdd, size)), beanClass).size();
        if (countNextPage > 0) {
            countNextPage += (long) size * (pagesToAdd);
        } else {
            for (int i = 0; i < pagesToAdd; i++) {
                long count = mongoTemplate.find(query.with(PageRequest.of(page + pagesAhead - i, size)), beanClass).size();
                countNextPage += count;
                if (count > 0 && (i + 2) < pagesToAdd) {
                    countNextPage += size * (pagesToAdd - i - 1);
                    break;
                }
            }
        }
        countNextPage += (long) size * page;
        return countNextPage;
    }

    protected String accentToRegexForMongoDBRegexSearch(String text) {
        text = text.toLowerCase();
        StringBuilder output = new StringBuilder();

        char[] charArray = text.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            int indexOfNoAccent = NO_ACCENT_STRINGS.indexOf(charArray[i]);
            if (indexOfNoAccent != -1) {
                int accentIndex = NO_ACCENT_STRINGS.indexOf(NO_ACCENT_STRINGS.charAt(indexOfNoAccent));
                output.append("[").append(NO_ACCENT_STRINGS.charAt(accentIndex)).append(ACCENT_STRINGS.charAt(accentIndex));
                while (accentIndex <= NO_ACCENT_STRINGS.length() && charArray[i] == NO_ACCENT_STRINGS.charAt(accentIndex)) {
                    output.append(ACCENT_STRINGS.charAt(accentIndex));
                    accentIndex++;
                }
                output.append("]");
            } else {
                output.append(charArray[i]);
            }
        }
        return output.toString();
    }

    protected Query handleSort(Query query, String sortField, Integer sortOrder) {
        if (sortField != null) {
            Sort sort = Sort.by(sortField);
            if (sortOrder != null) {
                if (sortOrder == 1) {
                    sort = sort.ascending();
                } else if (sortOrder == -1) {
                    sort = sort.descending();
                }
            }
            query = query.with(sort);
        }
        return query;
    }

    private String buscarContexto(String pacote) {
        List<String> pacoteList = List.of(pacote.split("\\."));
        return pacoteList.get(pacoteList.size() - 1).replace("Model", "");
    }

}
