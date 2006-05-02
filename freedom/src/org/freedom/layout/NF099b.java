/**
 * @version 10/12/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: leiautes <BR>
 * Classe: @(#)NF099.java <BR>
 * 
 * Este programa � licenciado de acordo com a LPG-PC (Licen�a P�blica Geral para Programas de Computador), <BR>
 * vers�o 2.1.0 ou qualquer vers�o posterior. <BR>
 * A LPG-PC deve acompanhar todas PUBLICA��ES, DISTRIBUI��ES e REPRODU��ES deste Programa. <BR>
 * Caso uma c�pia da LPG-PC n�o esteja dispon�vel junto com este Programa, voc� pode contatar <BR>
 * o LICENCIADOR ou ent�o pegar uma c�pia em: <BR>
 * Licen�a: http://www.lpg.adv.br/licencas/lpgpc.rtf <BR>
 * Para poder USAR, PUBLICAR, DISTRIBUIR, REPRODUZIR ou ALTERAR este Programa � preciso estar <BR>
 * de acordo com os termos da LPG-PC <BR> <BR>
 *
 * Layout da nota fiscal para a empresa 99 Ltda.
 */

package org.freedom.layout;

import java.math.BigDecimal;
import java.util.Calendar;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.NF;
import org.freedom.funcoes.Funcoes;
public class NF099b extends Layout {

	public boolean imprimir(NF nf,ImprimeOS imp) {
			 
		boolean bRetorno = super.imprimir(nf, imp);
		boolean bNat = true;
		int iNumNota = 0;
		int numMax = 42;
		int iItImp = 0;
		int iLinPag = imp.verifLinPag("NF");
		Calendar cHora = Calendar.getInstance();
		String[] sNat = new String[2];
		String sNumNota = ""; 
		String sTipoTran = "";
		String sHora;
		
		try {
			imp.limpaPags();
			
			sHora = Funcoes.strZero(String.valueOf(cHora.get(Calendar.HOUR_OF_DAY)),2) + ":" +
					Funcoes.strZero(String.valueOf(cHora.get(Calendar.MINUTE)),2) + ":" +
					Funcoes.strZero(String.valueOf(cHora.get(Calendar.SECOND)),2);
			  
			cab.next();
			
			iNumNota = cab.getInt(NF.C_DOC);
			
			if(iNumNota==0) 
				sNumNota = "000000";
			else 
				sNumNota = Funcoes.strZero(String.valueOf(iNumNota),6);
			     
			while (itens.next()) {  
				  
				if (bNat) {
					sNat[0] = Funcoes.copy(itens.getString(NF.C_DESCNAT),35);
					sNat[1] = Funcoes.setMascara(String.valueOf(itens.getInt(NF.C_CODNAT)),"#.##");					
					bNat = false;
				}
				 
				if (imp.pRow()==0) {
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					if (nf.getTipoNF()==NF.TPNF_ENTRADA)
						imp.say(imp.pRow(),105, "X");
					else
						imp.say(imp.pRow(), 88, "X");
					
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  6, sNat[0]);
					imp.say(imp.pRow(), 42, sNat[1]);					
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  6, cab.getString(NF.C_RAZEMIT));
					imp.say(imp.pRow(), 92, !cab.getString(NF.C_CPFEMIT).equals("") ? Funcoes.setMascara(cab.getString(NF.C_CPFEMIT),"###.###.###-##") : Funcoes.setMascara(cab.getString(NF.C_CNPJEMIT),"##.###.###/####-##")) ;
					imp.say(imp.pRow(),125, Funcoes.dateToStrDate(cab.getDate(NF.C_DTEMITPED)));
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  6, Funcoes.copy(cab.getString(NF.C_ENDEMIT),0,50).trim()+", "+(Funcoes.copy(""+cab.getInt(NF.C_NUMEMIT),0,6).trim()).trim()+" - "+(cab.getString(NF.C_COMPLEMIT) != null ? Funcoes.copy(cab.getString(NF.C_COMPLEMIT),0,9).trim() : "").trim());
					imp.say(imp.pRow(), 70, !cab.getString(NF.C_BAIREMIT).equals("") ? Funcoes.copy(cab.getString(NF.C_BAIREMIT),0,15) : "");
					imp.say(imp.pRow(), 98, Funcoes.setMascara(cab.getString(NF.C_CEPEMIT),"#####-###"));
					imp.say(imp.pRow(),125, Funcoes.dateToStrDate(cab.getDate(NF.C_DTSAIDA)));
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  6, cab.getString(NF.C_CIDEMIT));
					imp.say(imp.pRow(), 50, (!cab.getString(NF.C_DDDEMIT).equals("") ? "("+cab.getString(NF.C_DDDEMIT)+")" : "")+(!cab.getString(NF.C_FONEEMIT).equals("") ? Funcoes.setMascara(cab.getString(NF.C_FONEEMIT).trim(),"####-####") : ""));
					imp.say(imp.pRow(), 80, cab.getString(NF.C_UFEMIT));
					imp.say(imp.pRow(), 92, !cab.getString(NF.C_RGEMIT).equals("") ? cab.getString(NF.C_RGEMIT) : cab.getString(NF.C_INSCEMIT));
					imp.say(imp.pRow(),126, sHora);
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
				}	   
				
				imp.say(imp.pRow()+1,0, imp.comprimido());
				imp.say(imp.pRow(),  6, String.valueOf(itens.getInt(NF.C_CODPROD)));
				imp.say(imp.pRow(), 14, itens.getString(NF.C_DESCPROD));
				imp.say(imp.pRow(), 82, Funcoes.copy(itens.getString(NF.C_ORIGFISC),0,1)+Funcoes.copy(itens.getString(NF.C_CODTRATTRIB),0,2));
				imp.say(imp.pRow(), 87, itens.getString(NF.C_CODUNID).substring(0,4));
				imp.say(imp.pRow(), 94, Funcoes.strDecimalToStrCurrency( 6,2,String.valueOf(itens.getFloat(NF.C_QTDITPED))));
				imp.say(imp.pRow(),102, Funcoes.strDecimalToStrCurrency(12,2,String.valueOf(((new BigDecimal(itens.getFloat(NF.C_VLRLIQITPED))).divide(new BigDecimal(itens.getFloat(NF.C_QTDITPED)),2,BigDecimal.ROUND_HALF_UP)))));
				imp.say(imp.pRow(),116, Funcoes.strDecimalToStrCurrency(12,2,String.valueOf(itens.getFloat(NF.C_VLRLIQITPED))));
				imp.say(imp.pRow(),132, String.valueOf(itens.getFloat(NF.C_PERCICMSITPED)));
				     
				iItImp++;
				if ((iItImp == itens.getInt(NF.C_CONTAITENS)) || (imp.pRow() == numMax)) {       	 
					if (iItImp == itens.getInt(NF.C_CONTAITENS)) {
						int iRow = imp.pRow();						
						for (int i=0; i<(numMax-iRow);i++) {
							imp.say(imp.pRow()+1,0, imp.comprimido());
						}
							
						frete.next();
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow(),  6, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRBASEICMSPED))));
						imp.say(imp.pRow(), 32, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRICMSPED))));
						imp.say(imp.pRow(),116, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRLIQPED))));
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow(),  6, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(frete.getFloat(NF.C_VLRFRETEPED))));
						imp.say(imp.pRow(), 60, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRADICPED))));
						imp.say(imp.pRow(), 87, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRIPIPED))));
						imp.say(imp.pRow(),116, Funcoes.strDecimalToStrCurrency(20,2,String.valueOf(itens.getFloat(NF.C_VLRLIQPED))));
						iItImp = 0;
					}
					else if (imp.pRow() == numMax) {
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow(),  6, "***************");
						imp.say(imp.pRow(), 32, "***************");
						imp.say(imp.pRow(),116, "***************");
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow(),  6, "***************");
						imp.say(imp.pRow(), 62, "***************");
						imp.say(imp.pRow(), 87, "***************");
						imp.say(imp.pRow(),116, "***************");
					}
					
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					
					if (frete.getString(NF.C_TIPOFRETE)!=null) {   
						imp.say(imp.pRow(),  6, frete.getString(NF.C_RAZTRANSP));
						imp.say(imp.pRow(), 87, frete.getString(NF.C_TIPOFRETE).equals("C") ? "1" : "2");
						imp.say(imp.pRow(), 92, frete.getString(NF.C_PLACAFRETE));
						imp.say(imp.pRow(),104, frete.getString(NF.C_UFFRETE));
					}
					
					sTipoTran = frete.getString(NF.C_TIPOTRANSP);
					if (sTipoTran==null) 
						sTipoTran = "T";    
					if ( sTipoTran.equals("C") )
						imp.say(imp.pRow(),115, Funcoes.setMascara(cab.getString(NF.C_CNPJEMIT),"##.###.###/####-##"));
					else 
						imp.say(imp.pRow(),115, Funcoes.setMascara(frete.getString(NF.C_CNPJTRANSP),"##.###.###/####-##"));					   
					   
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  6, Funcoes.copy(frete.getString(NF.C_ENDTRANSP),0,42)+", "+Funcoes.copy(frete.getString(NF.C_NUMTRANSP),0,6));
					imp.say(imp.pRow(), 76, frete.getString(NF.C_CIDTRANSP));
					imp.say(imp.pRow(),104, frete.getString(NF.C_UFTRANSP));
					
					if (sTipoTran.equals("C") )
						imp.say(imp.pRow(),115, cab.getString(NF.C_INSCEMIT));
					else 
						imp.say(imp.pRow(),115, frete.getString(NF.C_INSCTRANSP));
					
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  6, String.valueOf(frete.getFloat(NF.C_QTDFRETE)));
					imp.say(imp.pRow(), 27, frete.getString(NF.C_ESPFRETE));
					imp.say(imp.pRow(), 55, frete.getString(NF.C_MARCAFRETE));
					imp.say(imp.pRow(),102, String.valueOf(frete.getFloat(NF.C_PESOBRUTO)));
					imp.say(imp.pRow(),125, String.valueOf(frete.getFloat(NF.C_PESOLIQ)));					 
										   
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),125, sNumNota);
					
					for (int i=imp.pRow(); i<=iLinPag; i++)  
						imp.say(imp.pRow()+1,0, imp.comprimido());
					
					imp.setPrc(0,0);
					imp.incPags();
				}
			}

			imp.fechaGravacao();
			bRetorno = true;
		} catch ( Exception err ) {
			Funcoes.mensagemErro(null,"Erro ao montar nf de venda.\n"+err.getMessage());     
			err.printStackTrace();
			bRetorno = false;
		}  finally {
			cHora = null;
			sNat = null;
			sNumNota = null; 
			sTipoTran = null;
			sHora = null;
			System.gc();
		}
		return bRetorno;
	}
}

