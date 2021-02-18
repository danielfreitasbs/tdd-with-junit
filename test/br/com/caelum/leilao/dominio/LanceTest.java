package br.com.caelum.leilao.dominio;

public class LanceTest {

	@Test(expected=IllegalArgumentException.class)
	public void naoDeveAceitarCriacaoDeLanceComValorZeroOuNegativo() {
		Usuario joao = new Usuario("João");
		Lance lance = new Lance(joao, -1.0);
	}
	
}
