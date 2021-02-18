package br.com.caelum.leilao.servico;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class TesteDoAvaliador {

	private Avaliador leiloeiro;

	@Before
	public void setUp() {
		this.leiloeiro = new Avaliador();
		System.out.println("inicializando teste!");
	}

	@After
	public void finaliza() {
		System.out.println("fim");
	}

	@Test(expected = RuntimeException.class)
	public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").constroi();
		leiloeiro.avalia(leilao);
	}

	@Test
	public void deveEntenderLancesEmOrdemCrescente() {
		// Parte 1: cenário
		Usuario joao = new Usuario("João");
		Usuario jose = new Usuario("José");
		Usuario maria = new Usuario("Maria");

		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 250.0));
		leilao.propoe(new Lance(jose, 300.0));
		leilao.propoe(new Lance(maria, 400.0));

		// Parte 2: Ação
		leiloeiro.avalia(leilao);

		// Parte 3: Validação
		double maiorEsperado = 400.0;
		double menorEsperado = 250.0;

		assertThat(maiorEsperado, equalTo(400.0));
		assertThat(menorEsperado, equalTo(250.0));
	}

	@Test
	public void deveEntenderLeilaoComAPenasUmLance() {
		Usuario joao = new Usuario("João");
		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 1000.0));

		leiloeiro.avalia(leilao);

		assertThat(leiloeiro.getMaiorLance(), equalTo(1000));
		assertThat(leiloeiro.getMenorLance(), equalTo(1000));
	}

	@Test
	public void deveEncontrarOsTresMaioresLances() {
		// Parte 1: cenário
		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");

		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 100.0));
		leilao.propoe(new Lance(maria, 200.0));
		leilao.propoe(new Lance(joao, 300.0));
		leilao.propoe(new Lance(maria, 400.0));

		leiloeiro.avalia(leilao);

		List<Lance> maiores = leiloeiro.getTresMaiores();
		assertEquals(3, maiores.size());
		assertThat(maiores, hasItems(new Lance(maria, 400), new Lance(joao, 300), new Lance(maria, 200)));
	}

	@Test
	public void deveEntenderLancesAleatorios() {
		Usuario joao = new Usuario("João");
		Usuario jaqueline = new Usuario("Jaqueline");
		Usuario pascoal = new Usuario("Pascoal");

		Leilao leilao = new Leilao("Parachoque do Gol");

		leilao.propoe(new Lance(joao, 200.0));
		leilao.propoe(new Lance(jaqueline, 450.0));
		leilao.propoe(new Lance(pascoal, 120.0));
		leilao.propoe(new Lance(joao, 700.0));
		leilao.propoe(new Lance(jaqueline, 630.0));
		leilao.propoe(new Lance(pascoal, 230.0));

		leiloeiro.avalia(leilao);

		assertThat(leiloeiro.getMenorLance(), equalTo(120));
		assertThat(leiloeiro.getMaiorLance(), equalTo(700));
	}

	@Test
	public void deveEntenderLancesDecrescentes() {
		Usuario joao = new Usuario("João");
		Usuario jaqueline = new Usuario("Jaqueline");

		Leilao leilao = new Leilao("Parachoque do Gol");

		leilao.propoe(new Lance(joao, 400.0));
		leilao.propoe(new Lance(joao, 300.0));
		leilao.propoe(new Lance(joao, 200.0));
		leilao.propoe(new Lance(joao, 100.0));

		leilao.getLances().forEach(l -> {
			System.out.println(l.getUsuario().getNome());
		});

		leiloeiro.avalia(leilao);

		assertThat(leiloeiro.getMenorLance(), equalTo(100));
		assertThat(leiloeiro.getMaiorLance(), equalTo(400));
	}

	@Test
	public void deveEncontrarMaioresComCincoLances() {
		// Parte 1: cenário
		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");

		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 100.0));
		leilao.propoe(new Lance(joao, 200.0));
		leilao.propoe(new Lance(joao, 300.0));
		leilao.propoe(new Lance(joao, 400.0));
		leilao.propoe(new Lance(joao, 500.0));

		leiloeiro.avalia(leilao);

		List<Lance> maiores = leiloeiro.getTresMaiores();

		assertEquals(3, maiores.size());
		assertThat(maiores, hasItems(new Lance(joao, 500), new Lance(joao, 400), new Lance(joao, 300)));
	}

	@Test
	public void deveEncontrarMaioresComDoisLances() {
		// Parte 1: cenário
		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");

		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 100.0));
		leilao.propoe(new Lance(joao, 200.0));

		leiloeiro.avalia(leilao);

		List<Lance> maiores = leiloeiro.getTresMaiores();

		assertEquals(1, maiores.size());
		assertThat(maiores.size(), equalTo(1));
		assertThat(maiores, hasItems(new Lance(joao, 200), new Lance(joao, 100)));
	}

	@Test(expected=RuntimeException.class)
	public void deveEncontrarMaioresComNenhumLance() {
		// Parte 1: cenário
		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");

		Leilao leilao = new Leilao("Playstation 3 Novo");

		leiloeiro.avalia(leilao);
	}
}
