package br.com.caelum.leilao.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao {

	private String descricao;
	private List<Lance> lances;

	public Leilao(String descricao) {
		this.descricao = descricao;
		this.lances = new ArrayList<Lance>();
	}

	public void propoe(Lance lance) {

		int countLances = 0;
		for (Lance l : lances) {
			if (l.getUsuario().equals(lance.getUsuario()))
				countLances++;
		}

		if (lances.isEmpty() || (!ultimoLanceDo(lance.getUsuario()) && countLances < 5)) {
			lances.add(lance);
		}
	}

	private boolean ultimoLanceDo(Usuario usuario) {
		return ultimoLance().getUsuario().equals(usuario);
	}

	public String getDescricao() {
		return descricao;
	}

	public List<Lance> getLances() {
		return Collections.unmodifiableList(lances);
	}

	public void dobraLance(Usuario joao) {
		if (!lances.isEmpty()) {
			double valorUltimoLance = ultimoLance().getValor();

			Lance lance = new Lance(joao, valorUltimoLance * 2);

			propoe(lance);
		}
	}

	private Lance ultimoLance() {
		return lances.get(lances.size() - 1);
	}

}
