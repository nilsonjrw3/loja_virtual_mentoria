package w3.lojavirtual;

import w3.lojavirtualw3.util.ValidaCNPJ;
import w3.lojavirtualw3.util.ValidaCPF;

public class TesteCPFCNPJ {
	
	public static void main(String[] args) {
		boolean isCNPJ = ValidaCNPJ.isCNPJ("78.885.145/0001-04");
		
		System.out.println("CNPJ Valido: "+ isCNPJ);
		
		boolean isCPF = ValidaCPF.isCPF("765.861.370-39");
		
		System.out.println("CPF Valido: "+isCPF);
	}
}
