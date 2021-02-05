package br.com.caelum.leilao.servico;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.builder.LeilaoBuilder;
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

	@Test
	public void deveEntenderLancesEmOrdemCrescente() {
		// Parte 1: cenário
		Usuario joao = new Usuario("João");
		Usuario jose = new Usuario("José");
		Usuario maria = new Usuario("Maria");

		Leilao leilao = new LeilaoBuilder("Play 3").lance(joao, 250.0).lance(joao, 300.0).lance(maria, 400.0).build();

		// Parte 2: Ação
		leiloeiro.avalia(leilao);

		// Parte 3: Validação
		double maiorEsperado = 400.0;
		double menorEsperado = 250.0;

		assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
	}

	@Test
	public void deveEntenderLeilaoComAPenasUmLance() {
		Usuario joao = new Usuario("João");
		
		Leilao leilao = new LeilaoBuilder("Parachoque do Gol").lance(joao, 1000.0).build();

		leiloeiro.avalia(leilao);

		assertEquals(1000.0, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(1000.0, leiloeiro.getMenorLance(), 0.00001);
	}

	@Test
	public void deveEncontrarOsTresMaioresLances() {
		// Parte 1: cenário
		Usuario joao = new Usuario("João");

		Leilao leilao = new LeilaoBuilder("Parachoque do Gol")
				.lance(joao, 100.0)
				.lance(joao, 200.0)
				.lance(joao, 300.0)
				.lance(joao, 400.0)
				.build();

		leiloeiro.avalia(leilao);

		List<Lance> maiores = leiloeiro.getTresMaiores();
		assertEquals(3, maiores.size());
		assertEquals(400.0, maiores.get(0).getValor(), 0.00001);
		assertEquals(300.0, maiores.get(1).getValor(), 0.00001);
		assertEquals(200.0, maiores.get(2).getValor(), 0.00001);
	}

	@Test
	public void deveEntenderLancesAleatorios() {
		Usuario joao = new Usuario("João");
		Usuario jaqueline = new Usuario("Jaqueline");
		Usuario pascoal = new Usuario("Pascoal");

		Leilao leilao = new LeilaoBuilder("Parachoque do Gol")
				.lance(joao, 200.0)
				.lance(jaqueline, 450.0)
				.lance(pascoal, 120.0)
				.lance(joao, 700.0)
				.lance(jaqueline, 630.0)
				.lance(pascoal, 230.0)
				.build();

		leiloeiro.avalia(leilao);

		assertEquals(120.0, leiloeiro.getMenorLance(), 0.00001);
		assertEquals(700.0, leiloeiro.getMaiorLance(), 0.00001);
	}

	@Test
	public void deveEntenderLancesDecrescentes() {
		Usuario joao = new Usuario("João");

		Leilao leilao = new LeilaoBuilder("Parachoque do Gol").lance(joao, 400.0).lance(joao, 300.0).lance(joao, 200.0)
				.lance(joao, 100.0).build();

		leilao.getLances().forEach(l -> {
			System.out.println(l.getUsuario().getNome());
		});

		leiloeiro.avalia(leilao);

		assertEquals(100.0, leiloeiro.getMenorLance(), 0.00001);
		assertEquals(400.0, leiloeiro.getMaiorLance(), 0.00001);
	}

	@Test
	public void deveEncontrarMaioresComCincoLances() {
		// Parte 1: cenário
		Usuario joao = new Usuario("João");

		Leilao leilao = new LeilaoBuilder("Parachoque do Gol")
				.lance(joao, 100.0)
				.lance(joao, 200.0)
				.lance(joao, 300.0)
				.lance(joao, 400.0)
				.lance(joao, 500.0)
				.build();

		leiloeiro.avalia(leilao);

		List<Lance> maiores = leiloeiro.getTresMaiores();

		assertEquals(3, maiores.size());
		assertEquals(500.0, maiores.get(0).getValor(), 0.00001);
		assertEquals(400.0, maiores.get(1).getValor(), 0.00001);
		assertEquals(300.0, maiores.get(2).getValor(), 0.00001);

	}

	@Test
	public void deveEncontrarMaioresComDoisLances() {
		// Parte 1: cenário
		Usuario joao = new Usuario("João");

		Leilao leilao = new LeilaoBuilder("Parachoque do Gol")
				.lance(joao, 100.0)
				.lance(joao, 200.0)
				.build();

		leiloeiro.avalia(leilao);

		List<Lance> maiores = leiloeiro.getTresMaiores();

		assertEquals(1, maiores.size());
		assertEquals(200.0, maiores.get(0).getValor(), 0.00001);
		assertEquals(100.0, maiores.get(1).getValor(), 0.00001);
	}

	@Test
	public void deveEncontrarMaioresComNenhumLance() {
		// Parte 1: cenário
		Leilao leilao = new LeilaoBuilder("Parachoque do Gol")
				.build();

		leiloeiro.avalia(leilao);

		List<Lance> maiores = leiloeiro.getTresMaiores();

		assertEquals(0, maiores.size());
	}
}
