 /**
 * @version 10/12/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Alex Rodrigues <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: leiautes <BR>
 * Classe: @(#)NFBuzzi2.java <BR>
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
 * Layout da nota fiscal para a empresa Iswara Ltda.
 */
package org.freedom.layout;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Vector;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.NF;
import org.freedom.funcoes.Funcoes;

public class NF021 extends Layout {
  
	public boolean imprimir(NF nf,ImprimeOS imp) {
		boolean retorno = super.imprimir(nf, imp);
		Calendar cHora = Calendar.getInstance();
		Vector vValores = new Vector();
		Vector vClfisc = new Vector();
		Vector vSigla = new Vector();
		Vector vDesc = null;
		String sDesc = null;
		String sCodfisc = null;
		String sSigla = null;   
		String sTipoTran=  null;
		String sHora = null;
		String[] sNat = new String[4];
		int iProd = 0;
		boolean bMensage = false;
		boolean bTotalizou = false;
		boolean bjatem = false;
		boolean bNat = true;
			
		try {
					
			imp.limpaPags();
			
			vClfisc.addElement("");
			vClfisc.addElement("8302490000");
			vClfisc.addElement("7318220000");
			vClfisc.addElement("8302200000");
			vClfisc.addElement("3901200100");
			vClfisc.addElement("7317000201");
			
			sHora = Funcoes.strZero(String.valueOf(cHora.get(Calendar.HOUR_OF_DAY)),2) + ":" + 
					Funcoes.strZero(String.valueOf(cHora.get(Calendar.MINUTE)),2) + ":" + 
					Funcoes.strZero(String.valueOf(cHora.get(Calendar.SECOND)),2);
			
			cab.next();		
			
						
			while (itens.next()) {
									
				if (bNat) {
					sNat[0] = itens.getString(NF.C_DESCNAT);
					sNat[1] = Funcoes.setMascara(itens.getString(NF.C_CODNAT),"#.##");
					bNat = false;
				}
				
				if (imp.pRow()==0) {
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					
					if (nf.getTipoNF()==NF.TPNF_ENTRADA)
						imp.say(imp.pRow()+0, 99,"X");
					else
						imp.say(imp.pRow()+0, 87,"X");
					
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  6, sNat[0]);
					imp.say(imp.pRow(), 54, sNat[1]);					
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  6, cab.getInt(NF.C_CODEMIT)+" - "+cab.getString(NF.C_RAZEMIT));
					imp.say(imp.pRow(), 90, !cab.getString(NF.C_CPFEMIT).equals("") ? Funcoes.setMascara(cab.getString(NF.C_CPFEMIT),"###.###.###-##") : Funcoes.setMascara(cab.getString(NF.C_CNPJEMIT),"##.###.###/####-##")) ;
					imp.say(imp.pRow(),126, Funcoes.dateToStrDate(cab.getDate(NF.C_DTEMITPED)));					
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  6, Funcoes.copy(cab.getString(NF.C_ENDEMIT),0,50).trim()+", "+(!cab.getString(NF.C_NUMEMIT).equals("") ? Funcoes.copy(cab.getString(NF.C_NUMEMIT),0,6).trim() : "").trim()+" - "+(!cab.getString(NF.C_COMPLEMIT).equals("") ? Funcoes.copy(cab.getString(NF.C_COMPLEMIT),0,9).trim() : "").trim());
					imp.say(imp.pRow(), 80, Funcoes.copy(cab.getString(NF.C_BAIREMIT),0,20));
					imp.say(imp.pRow(),107, Funcoes.setMascara(cab.getString(NF.C_CEPEMIT),"#####-###"));
					imp.say(imp.pRow(),126, Funcoes.dateToStrDate(cab.getDate(NF.C_DTSAIDA)));					
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  8, cab.getString(NF.C_CIDEMIT));
					imp.say(imp.pRow(), 54, (Funcoes.setMascara(cab.getString(NF.C_DDDEMIT) + " ","(####)")) + 
											(Funcoes.setMascara(cab.getString(NF.C_FONEEMIT).trim(),"####-####")));
					imp.say(imp.pRow(), 80, cab.getString(NF.C_UFEMIT));
					imp.say(imp.pRow(), 90, !cab.getString(NF.C_RGEMIT).equals("") ? cab.getString(NF.C_RGEMIT) : cab.getString(NF.C_INSCEMIT));
					imp.say(imp.pRow(),127, sHora);
					
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					
				}
				 
				imp.say(imp.pRow()+1,0, imp.comprimido());                    
				
				vDesc = Funcoes.strToVectorSilabas(itens.getString(NF.C_OBSITPED)==null || itens.getString(NF.C_OBSITPED).equals("") ? (itens.getString(NF.C_DESCPROD).trim()):itens.getString(NF.C_OBSITPED),50);
				for (int iConta=0;( (iConta < 20) && (vDesc.size()>iConta) );iConta++) {
					if (!vDesc.elementAt(iConta).toString().equals(""))					
						sDesc = vDesc.elementAt(iConta).toString();
					else					
						sDesc = "";
					
					if (iConta > 0)
						imp.say(imp.pRow()+1,0, imp.comprimido());
					
					imp.say(imp.pRow(),  8, sDesc);
					iProd = iProd+vDesc.size();					
				}
				
				sCodfisc = itens.getString(NF.C_CODFISC);				   
				if(!sCodfisc.equals("")){
					for(int i=0;i<vClfisc.size();i++){
						if(vClfisc.elementAt(i)!=null){
							if(sCodfisc.equals((String)vClfisc.elementAt(i))){
								bjatem = true;
								sSigla = String.valueOf((char)(64 + i));
							} else
								bjatem = false;
						}
					}
					if(!bjatem){
						vClfisc.addElement(sCodfisc);
						sSigla = String.valueOf((char)(63 + vClfisc.size()));
						vSigla.addElement(sCodfisc);
					}
				}
				
				imp.say(imp.pRow(), 63, sSigla);				
				imp.say(imp.pRow(), 67, Funcoes.copy(itens.getString(NF.C_ORIGFISC),0,1)+Funcoes.copy(itens.getString(NF.C_CODTRATTRIB),0,2));
				imp.say(imp.pRow(), 71, itens.getString(NF.C_CODUNID).substring(0,4));
				imp.say(imp.pRow(), 75, Funcoes.alinhaDir(""+itens.getFloat(NF.C_QTDITPED),6));
				imp.say(imp.pRow(), 82, Funcoes.strDecimalToStrCurrency(12,2,""+((new BigDecimal(itens.getFloat(NF.C_VLRLIQITPED))).divide(new BigDecimal(itens.getFloat(NF.C_QTDITPED)),2,BigDecimal.ROUND_HALF_UP))));
				imp.say(imp.pRow(),100, Funcoes.strDecimalToStrCurrency(13,2,itens.getFloat(NF.C_VLRLIQITPED)+""));
				imp.say(imp.pRow(),115, String.valueOf(itens.getFloat(NF.C_PERCICMSITPED)));
				imp.say(imp.pRow(),120, String.valueOf(itens.getFloat(NF.C_PERCIPIITPED)));
				imp.say(imp.pRow(),128, Funcoes.strDecimalToStrCurrency(7,2,itens.getFloat(NF.C_VLRIPIITPED)+""));
				
				if((iProd) >= 18){
					bMensage = true;
					break;
				}
				
				if (!bTotalizou) {
					frete.next();
						
					vValores.addElement(String.valueOf(itens.getFloat(NF.C_VLRBASEICMSPED))); //0
					vValores.addElement(String.valueOf(itens.getFloat(NF.C_VLRICMSPED))); //1
					vValores.addElement(String.valueOf(itens.getFloat(NF.C_VLRLIQPED))); // 2
					vValores.addElement(String.valueOf(frete.getFloat(NF.C_VLRFRETEPED)));//3
					vValores.addElement(String.valueOf(itens.getFloat(NF.C_VLRADICPED)));//4
					vValores.addElement(String.valueOf(itens.getFloat(NF.C_VLRIPIPED)));//5
					vValores.addElement(String.valueOf((itens.getFloat(NF.C_VLRLIQPED) - frete.getFloat(NF.C_VLRFRETEPED) + itens.getFloat(NF.C_VLRADICPED) - itens.getFloat(NF.C_VLRIPIPED))));//6
					vValores.addElement(frete.getString(NF.C_RAZTRANSP));//7
					vValores.addElement(frete.getString(NF.C_TIPOFRETE));//8
					vValores.addElement(frete.getString(NF.C_PLACAFRETE));//9
					vValores.addElement(frete.getString(NF.C_UFFRETE));      //10   
					sTipoTran = frete.getString(NF.C_TIPOTRANSP);
					vValores.addElement(sTipoTran);//11
					vValores.addElement(cab.getString(NF.C_CNPJEMIT));//12
					vValores.addElement(frete.getString(NF.C_CNPJTRANSP));   //13         
					vValores.addElement(frete.getString(NF.C_ENDTRANSP));//14
					   
					if (sTipoTran.equals("C")) {
						vValores.addElement("");//15
						vValores.addElement("");//16
						vValores.addElement("");//17
						vValores.addElement(""); //18
					} else {
						vValores.addElement(String.valueOf(frete.getInt(NF.C_NUMTRANSP)));//15
						vValores.addElement(frete.getString(NF.C_CIDTRANSP));//16
						vValores.addElement(frete.getString(NF.C_UFTRANSP));//17
						vValores.addElement(frete.getString(NF.C_INSCTRANSP)); //18
					}
					
					vValores.addElement(String.valueOf(frete.getFloat(NF.C_QTDFRETE)));//19
					vValores.addElement(frete.getString(NF.C_ESPFRETE));//20
					vValores.addElement(frete.getString(NF.C_MARCAFRETE));//21
					vValores.addElement(String.valueOf(frete.getFloat(NF.C_PESOBRUTO)));//22
					vValores.addElement(String.valueOf(frete.getFloat(NF.C_PESOLIQ)));//23
					vValores.addElement(String.valueOf(itens.getFloat(NF.C_VLRISSPED)));//24
					vValores.addElement(String.valueOf(cab.getInt(NF.C_CODVEND)));//25
					
					if (cab.getString(NF.C_NOMEVEND).equals(""))
						vValores.addElement(Funcoes.replicate(" ",25)); // 26
					else 
						vValores.addElement(cab.getString(NF.C_NOMEVEND)+Funcoes.replicate(" ",25-cab.getString(NF.C_NOMEVEND).length()));
							
					bTotalizou = true;
				} 
				
			}			
				
			impTotais(imp,vValores);		
			
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow()+1,0, imp.comprimido());
			
			if(vSigla.size()>0) {
				imp.say(imp.pRow()+1,0, imp.comprimido());
				imp.say(imp.pRow()+0,9,(String)vSigla.elementAt(0));
			} 
			
			retorno = true;
			
			if(bMensage) {
				Funcoes.mensagemInforma(null,"Erro ao montar disposi��o da nota fiscal!\n" + 
							"N�mero maixmo de produtos exede.");
				retorno = false;					
			}
			if(vSigla.size()>1)
				Funcoes.mensagemInforma(null,"Existem classifica��es fiscais n�o relacionadas.");
										
			imp.fechaGravacao();
			
		} catch ( Exception err ) {
			err.printStackTrace();
			Funcoes.mensagemErro(null,"Erro ao montar Nota Fiscal!"+err.getMessage());      
			retorno = false;
		} finally {
			cHora = null;
			vValores = null;
			vClfisc = null;
			vSigla = null;
			vDesc = null;
			sDesc = null;
			sCodfisc = null;
			sSigla = null;   
			sTipoTran=  null;
			sHora = null;
			sNat = null; 
			System.gc();
		}
			
		return retorno;
	}
			
	private void impTotais(ImprimeOS imp,Vector vValores){
		String sTipoTran = null;
		try {	
			for (int i=0;(imp.pRow()<40);i++)
				imp.say(imp.pRow()+1,0, imp.comprimido());
			
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow(),  6, Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(0).toString()));
			imp.say(imp.pRow(), 35, Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(1).toString()));
			imp.say(imp.pRow(),117, Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(6).toString()));
			
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow()+1,0, imp.comprimido());
			
			imp.say(imp.pRow(),  6, Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(3).toString()));
			imp.say(imp.pRow(), 62, Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(4).toString()));
			imp.say(imp.pRow(), 88, Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(5).toString()));
			imp.say(imp.pRow(),117, Funcoes.strDecimalToStrCurrency(20,2,vValores.elementAt(2).toString()));
			
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow()+1,0, imp.comprimido());
			    	        	       	      	      
			imp.say(imp.pRow(),  6, vValores.elementAt(7) != null ? vValores.elementAt(7).toString() : "");
			imp.say(imp.pRow(), 73, vValores.elementAt(8) != null ? (vValores.elementAt(8).toString().equals("C") ? "1" : "2") : "");
			imp.say(imp.pRow(), 79, vValores.elementAt(9) != null ? vValores.elementAt(9).toString() : "");
			imp.say(imp.pRow(),101, vValores.elementAt(10) != null ? vValores.elementAt(10).toString() : "");
			  
			sTipoTran = vValores.elementAt(11).toString();
						  
			if ( sTipoTran.equals("C") )
				imp.say(imp.pRow(),110, "");			  	
			else 
				imp.say(imp.pRow(),110, Funcoes.setMascara(vValores.elementAt(13) != null ? vValores.elementAt(13).toString() : "","##.###.###/####-##"));
			
			  
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow()+1,0, imp.comprimido());
			
			imp.say(imp.pRow(),  6, vValores.elementAt(14) != null ? vValores.elementAt(14).toString().trim() : "");
			imp.say(imp.pRow(), 72, vValores.elementAt(16) != null ? vValores.elementAt(16).toString().trim() : "");
			imp.say(imp.pRow(),101, vValores.elementAt(17) != null ? vValores.elementAt(17).toString().trim() : "");
			imp.say(imp.pRow(),117, vValores.elementAt(18) != null ? vValores.elementAt(18).toString() : "");
			    	      
			imp.say(imp.pRow()+1,0, imp.comprimido());
			imp.say(imp.pRow()+1,0, imp.comprimido());
			  
			imp.say(imp.pRow(),  6, vValores.elementAt(19) != null ? vValores.elementAt(19).toString() : "");
			imp.say(imp.pRow(), 23, vValores.elementAt(20) != null ? vValores.elementAt(20).toString() : "");
			imp.say(imp.pRow(), 48, vValores.elementAt(21) != null ? vValores.elementAt(21).toString() : "");
			imp.say(imp.pRow(),100, vValores.elementAt(22) != null ? vValores.elementAt(22).toString() : "");
			imp.say(imp.pRow(),128, vValores.elementAt(23) != null ? vValores.elementAt(23).toString() : "");
					  			
		} catch (Exception e) {
			e.printStackTrace();
			Funcoes.mensagemErro(null, "Erro ao montar layoude de nota fiscal.\n" + e.getMessage());
		} finally {
			sTipoTran = null;
		}
	}
}
