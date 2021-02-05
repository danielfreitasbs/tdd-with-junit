package br.com.caelum.leilao.builder;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class LeilaoBuilder {

	private Leilao leilao;

	public LeilaoBuilder(String description) {
		this.leilao = new Leilao(description);
	}

	public LeilaoBuilder lance(Usuario joao, double value) {
		leilao.propoe(new Lance(joao, value));
		return this;
	}

	public Leilao build() {
		return this.leilao;
	}

}
