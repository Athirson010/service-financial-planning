package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.model.GastoModel;
import io.github.athirson010.financialPlanning.repository.GastoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GastoService extends AbstractService<GastoModel, GastoRespository> {
    @Autowired
    UsuarioService usuarioService;

    public GastoService(GastoRespository repository) {
        super(GastoModel.class, repository);
    }

    @Override
    public GastoModel save(GastoModel model) {
        model.setUsuario(usuarioService.buscarUsuarioLogado());
        return super.save(model);
    }
}
