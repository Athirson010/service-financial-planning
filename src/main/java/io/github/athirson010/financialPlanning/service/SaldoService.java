package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.model.SaldoModel;
import io.github.athirson010.financialPlanning.repository.SaldoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SaldoService extends AbstractService<SaldoModel, SaldoRepository> {
    @Autowired
    UsuarioService usuarioService;

    public SaldoService(SaldoRepository repository) {
        super(SaldoModel.class, repository);
    }

    @Override
    public SaldoModel save(SaldoModel model) {
        model.setUsuario(usuarioService.buscarUsuarioLogado());
        return super.save(model);
    }

}
