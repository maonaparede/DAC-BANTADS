
package com.tads.dac.conta.service;

import com.tads.dac.conta.DTOs.ContaDTO;
import com.tads.dac.conta.DTOs.OperacaoBdDTO;
import com.tads.dac.conta.DTOs.OperacaoDTO;
import com.tads.dac.conta.exception.ClienteNotFoundException;
import com.tads.dac.conta.exception.InvalidMovimentacaoException;
import com.tads.dac.conta.exception.InvalidValorException;
import com.tads.dac.conta.mensageria.ProducerContaSync;
import com.tads.dac.conta.mensageria.ProducerGerenteSaldoSync;
import com.tads.dac.conta.modelCUD.ContaCUD;
import com.tads.dac.conta.modelCUD.OperacaoCUD;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tads.dac.conta.repositoryCUD.ContaRepositoryCUD;
import com.tads.dac.conta.repositoryCUD.OperacaoRepositoryCUD;
import com.tads.dac.conta.repositoryR.OperacaoRepositoryR;
import org.modelmapper.ModelMapper;

@Service
public class OperacaoServiceCUD{
    
    @Autowired
    private ContaRepositoryCUD repConta;
    
    @Autowired
    private OperacaoRepositoryCUD repOpCUD;
    
    @Autowired
    private OperacaoRepositoryR repOpR;
    
    @Autowired
    private ProducerContaSync contaSyncProducer;
    
    @Autowired
    private ProducerGerenteSaldoSync gerenteSyncProducer;
    
    @Autowired
    private ModelMapper mapper;

    
    public List<OperacaoDTO> getExtrato(Long id, Date inicio, Date fim) throws ClienteNotFoundException{
       
        List<Tuple> tuples = repOpR.getExtrato(id, inicio, fim);
        
        
        List<OperacaoDTO> dtos = new ArrayList<>();
        //Mappeamento na mão
        for (Tuple tuple : tuples) {
            
            OperacaoDTO dto = new OperacaoDTO();
            dto.setId(Long.valueOf(tuple.get("id").toString()));
            dto.setDataTempo((Date) tuple.get("data_tempo"));
            dto.setValor(new BigDecimal(tuple.get("valor").toString()));
            dto.setOperacao(tuple.get("operacao").toString());
            if(tuple.get("de_user") != null){
                dto.setDeUser(Long.valueOf(tuple.get("de_user").toString()));
            }
            if(tuple.get("para_user") != null){
                dto.setParaUser(Long.valueOf(tuple.get("para_user").toString()));
            }
            
            dtos.add(dto);
            
        }
        return dtos;
    }

    
    public OperacaoBdDTO fazDeposito(Long contaId, BigDecimal valor) throws ClienteNotFoundException, InvalidValorException {
        OperacaoCUD op = new OperacaoCUD();
        try {
            Optional<ContaCUD> ct = repConta.findById(contaId);
            if (!ct.isPresent()) throw new ClienteNotFoundException("O Cliente Que Faz a Transferência Não Existe.");
            if(valor.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidValorException("O valor de Deposito Tem Que ser Maior Que Zero");
            
            op.setParaUser(ct.get());
            op.setDeUser(null);
            op.setValor(valor);
            op.setOperacao("D");
            op.setDataTempo(null);

            op = repOpCUD.save(op);
            
            OperacaoBdDTO dto = mapper.map(op, OperacaoBdDTO.class);
            contaSyncProducer.syncOperacao(dto); //Manda msg

            BigDecimal saldo = ct.get().getSaldo().add(valor); // Adiciona ao Saldo
            updateSaldo(contaId, saldo);

            return dto;
            
        } catch (ClienteNotFoundException e) {
            throw new ClienteNotFoundException("O Cliente com essa conta não existe");
        }
    }

    
    public OperacaoBdDTO fazSaque(Long contaId, BigDecimal valor) throws ClienteNotFoundException, InvalidMovimentacaoException, InvalidValorException {
        OperacaoCUD op = new OperacaoCUD();
        try {
            Optional<ContaCUD> ct = repConta.findById(contaId);
            if (!ct.isPresent()) throw new ClienteNotFoundException("O Cliente Requerendo o Saque Não Existe.");
            if(valor.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidValorException("O valor de Transferencia Tem Que ser Maior Que Zero");
            
            BigDecimal valorPossivel = ct.get().getSaldo().add(ct.get().getLimite()); // O Valor Máximo de Saque é Saldo + Limite

            if (valorPossivel.compareTo(valor) < 0) throw new InvalidMovimentacaoException("Seu Limite e Saldo Não Permitem Essa Operação.");         

            op.setParaUser(null);
            op.setDeUser(ct.get());
            op.setValor(valor);
            op.setOperacao("S");
            op.setDataTempo(null);

            op = repOpCUD.save(op);
            
            OperacaoBdDTO dto = mapper.map(op, OperacaoBdDTO.class);
            contaSyncProducer.syncOperacao(dto);

            BigDecimal saldo = ct.get().getSaldo().subtract(valor); // Subtrai do Saldo

            updateSaldo(contaId, saldo);

            return dto;
        } catch (ClienteNotFoundException e) {
            throw new ClienteNotFoundException("O Cliente com essa conta não existe");
        }
    }

    public OperacaoBdDTO fazTransferencia(Long deId, Long paraId, BigDecimal valor) throws ClienteNotFoundException, InvalidMovimentacaoException, InvalidValorException {
        OperacaoCUD op = new OperacaoCUD();
        try {
            
            Optional<ContaCUD> ctDe = repConta.findById(deId);
            Optional<ContaCUD> ctPara = repConta.findById(paraId);
            
            if(!ctDe.isPresent()) throw new ClienteNotFoundException("O Cliente Que Faz a Transferência Não Existe.");
            if(!ctPara.isPresent()) throw new ClienteNotFoundException("O Cliente Que Recebe a Transferência Não Existe.");
            
            if(valor.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidValorException("O valor de Transferencia Tem Que ser Maior Que Zero");

            BigDecimal valorPossivel = ctDe.get().getSaldo().add(ctDe.get().getLimite());

            if (valorPossivel.compareTo(valor) < 0) throw new InvalidMovimentacaoException("Seu Limite e Saldo Não Permitem Essa Operação.");

            op.setId(null);
            op.setParaUser(ctPara.get());
            op.setDeUser(ctDe.get());
            op.setValor(valor);
            op.setOperacao("T");
            op.setDataTempo(null); 
            
            op = repOpCUD.save(op); // Salva Operação
            
            OperacaoBdDTO dto = mapper.map(op, OperacaoBdDTO.class);
            contaSyncProducer.syncOperacao(dto); //Manda msg

            BigDecimal saldoDe = ctDe.get().getSaldo().subtract(valor); // Subtrai do remetente
            updateSaldo(deId, saldoDe);
            
            BigDecimal saldoPara = ctPara.get().getSaldo().add(valor); // Adiciona ao destinatario
            updateSaldo(paraId, saldoPara);    
            
            return dto;
        } catch (ClienteNotFoundException e) {
            throw new ClienteNotFoundException("O Cliente com essa conta não existe");
        }    
    }
    
    public ContaCUD updateSaldo(Long contaId, BigDecimal saldo) throws ClienteNotFoundException {
        Optional<ContaCUD> conta = repConta.findById(contaId);
        if(conta.isPresent()){
            ContaCUD ct = conta.get();
            ct.setSaldo(saldo);
            ct = repConta.save(ct);
            ContaDTO dto = mapper.map(ct, ContaDTO.class);
            contaSyncProducer.syncConta(dto); //Sincroniza o bd de read
            gerenteSyncProducer.syncClienteSaldo(contaId, saldo); //Manda o Saldo Pro Modulo do Gerente
            return ct;
        }else{
            throw new ClienteNotFoundException("O Cliente com essa conta ("+ contaId+") não existe");
        }
    }

    
}
