package br.com.caelum.leilao.dominio;

public class LanceTest {

	@Test(expected=IllegalArgumentException.class)
	public void naoDeveAceitarCriacaoDeLanceComValorZeroOuNegativo() {
		Usuario joao = new Usuario("Jo�o");
		Lance lance = new Lance(joao, -1.0);
	}
	
}
