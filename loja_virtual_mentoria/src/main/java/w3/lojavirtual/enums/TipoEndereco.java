package w3.lojavirtual.enums;

public enum TipoEndereco {

	COBRANCA("Cobranca"),
	ENTREGA("Entrega");
	
	private String descricao;

	
	private TipoEndereco(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	
	
}
