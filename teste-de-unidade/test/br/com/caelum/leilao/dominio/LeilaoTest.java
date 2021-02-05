package br.com.caelum.leilao.dominio;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LeilaoTest {

	@Test
	public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {

		Usuario joao = new Usuario("João");
		Leilao leilao = new Leilao("Playstation 3 Novo");
		leilao.propoe(new Lance(joao, 250.0));
		leilao.propoe(new Lance(joao, 300.0));

		assertEquals(1, leilao.getLances().size());
		assertEquals(250.0, leilao.getLances().get(0).getValor(), 0.0001);
	}

	@Test
	public void naoDeveAceitarMaisDoQue5LancesDeUmMesmoUsuario() {
		Usuario joao = new Usuario("João");
		Usuario jose = new Usuario("Jose");

		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 250.0));
		leilao.propoe(new Lance(jose, 300.0));

		leilao.propoe(new Lance(joao, 450.0));
		leilao.propoe(new Lance(jose, 500.0));

		leilao.propoe(new Lance(joao, 550.0));
		leilao.propoe(new Lance(jose, 600.0));

		leilao.propoe(new Lance(joao, 650.0));
		leilao.propoe(new Lance(jose, 700.0));

		leilao.propoe(new Lance(joao, 750.0));
		leilao.propoe(new Lance(jose, 800.0));

		leilao.propoe(new Lance(joao, 950.0));

		assertEquals(10, leilao.getLances().size());
		assertEquals(800.0, leilao.getLances().get(leilao.getLances().size() - 1).getValor(), 0.0001);
	}

	@Test
	public void naoDeveDobrarLanceComLeilaoVazio() {
		Usuario joao = new Usuario("João");

		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.dobraLance(joao);

		assertEquals(0, leilao.getLances().size());
	}

	@Test
	public void deveDobraLance() {
		Usuario joao = new Usuario("João");
		Usuario jose = new Usuario("Jose");

		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 250.0));
		leilao.propoe(new Lance(jose, 300.0));

		leilao.propoe(new Lance(joao, 450.0));

		leilao.dobraLance(jose);

		assertEquals(4, leilao.getLances().size());
		assertEquals(900.0, leilao.getLances().get(leilao.getLances().size() - 1).getValor(), 0.0001);
	}

}
