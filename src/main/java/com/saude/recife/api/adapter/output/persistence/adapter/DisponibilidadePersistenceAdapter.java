package com.saude.recife.api.adapter.output.persistence.adapter;

import com.saude.recife.api.adapter.output.persistence.entity.DisponibilidadeEntity;
import com.saude.recife.api.adapter.output.persistence.mapper.DisponibilidadePersistenceMapper;
import com.saude.recife.api.adapter.output.persistence.repository.DisponibilidadeRepository;
import com.saude.recife.api.application.port.output.DisponibilidadePort;
import com.saude.recife.api.domain.model.Disponibilidade;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
public class DisponibilidadePersistenceAdapter
        implements DisponibilidadePort {

    private final DisponibilidadeRepository repository;
    private final DisponibilidadePersistenceMapper mapper;

    public DisponibilidadePersistenceAdapter(
            DisponibilidadeRepository repository,
            DisponibilidadePersistenceMapper mapper) {

        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Disponibilidade> consultar(
            LocalDate data,
            Long clinicaId,
            Long especialidadeId,
            Long profissionalId) {

        return repository.consultar(
                        data,
                        clinicaId,
                        especialidadeId,
                        profissionalId
                )
                .stream()
                .map(mapper::paraDomain)
                .toList();
    }

    @Override
    public boolean existeSobreposicao(
            Long profissionalId,
            LocalDate data,
            LocalTime horaInicio,
            LocalTime horaFim) {

        return repository.contarSobreposicoes(
                profissionalId,
                data,
                horaInicio,
                horaFim
        ) > 0;
    }

    @Override
    public boolean possuiAssociacoesValidas(Long idDisponibilidade) {
        return repository.possuiAssociacoesValidas(idDisponibilidade);
    }

    @Override
    public Optional<Disponibilidade> buscarPorId(Long id) {

        return repository
                .findById(id)
                .map(mapper::paraDomain);
    }

    @Override
    public Optional<Disponibilidade> buscarPorIdEProfissional(
            Long id,
            Long idProfissional) {
        return repository.findByIdAndIdProfissional(id, idProfissional)
                .map(mapper::paraDomain);
    }

    @Override
    public List<Disponibilidade> listarPorProfissional(Long idProfissional) {
        return repository.findByIdProfissionalOrderByDataAscHoraInicioAsc(idProfissional)
                .stream()
                .map(mapper::paraDomain)
                .toList();
    }

    @Override
    public Disponibilidade salvar(Disponibilidade disponibilidade) {
        var entity = new DisponibilidadeEntity(
                disponibilidade.getId(),
                disponibilidade.getIdProfissional(),
                disponibilidade.getIdClinica(),
                disponibilidade.getIdEspecialidade(),
                disponibilidade.getData(),
                disponibilidade.getHoraInicio(),
                disponibilidade.getHoraFim(),
                disponibilidade.isAtivo()
        );
        return mapper.paraDomain(repository.save(entity));
    }

    @Override
    public List<Disponibilidade> salvarTodas(List<Disponibilidade> disponibilidades) {
        return repository.saveAll(
                        disponibilidades.stream()
                                .map(disponibilidade -> new DisponibilidadeEntity(
                                        disponibilidade.getId(),
                                        disponibilidade.getIdProfissional(),
                                        disponibilidade.getIdClinica(),
                                        disponibilidade.getIdEspecialidade(),
                                        disponibilidade.getData(),
                                        disponibilidade.getHoraInicio(),
                                        disponibilidade.getHoraFim(),
                                        disponibilidade.isAtivo()
                                ))
                                .toList()
                )
                .stream()
                .map(mapper::paraDomain)
                .toList();
    }
}