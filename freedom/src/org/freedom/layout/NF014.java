/**
 * @version 10/12/2003 <BR>
 * @author Setpoint Inform�tica Ltda./Marco Antonio Sanchez <BR>
 *
 * Projeto: Freedom <BR>
 * Pacote: leiautes <BR>
 * Classe: @(#)NFIswara.java <BR>
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
 * Layout da nota fiscal para a empresa ModelCraft
 */

package org.freedom.layout;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Vector;

import org.freedom.componentes.ImprimeOS;
import org.freedom.componentes.NF;
import org.freedom.funcoes.Funcoes;

public class NF014 extends Layout {

	public boolean imprimir(NF nf,ImprimeOS imp) {
		
		boolean bRetorno = super.imprimir(nf, imp);
		final int iLinMax = 42;
		int iNumNota = 0;
		int iItImp = 0;
		int iLinPag = imp.verifLinPag("NF");
		int iContaMens = 1;
		int iRow = 0;
		boolean bjatem = false;
		boolean bFat = true;
		boolean bNat = true;
		String sDescAdic = "";
		String sCodfisc = "";
		String sClasFisc = "";
		String sSigla = "";
		String sNumNota = ""; 
		String sTipoTran = "";
		String sObs = "";
		String sDesc = "";
		String sHora = null;
		String sTmp = null;
		String[] sNat = new String[2];
		String[] sVencs = new String[4];
		String[] sVals = new String[4];
		String[] sDuplics = new String[4];
		String[] moeda = new String[]{"centavos","centavo","real","reais"};
		Vector vClfiscal = new Vector();
		Vector vMatObs = null;
		Vector vMens = new Vector();
		Vector vObs = null;
		Vector vSigla = new Vector();
		Calendar cHora = Calendar.getInstance();
		
		try {
			imp.limpaPags();
			
			sHora = Funcoes.strZero(String.valueOf(cHora.get(Calendar.HOUR_OF_DAY)), 2 ) + ":" + 
					Funcoes.strZero(String.valueOf(cHora.get(Calendar.MINUTE)), 2 ) + ":" + 
					Funcoes.strZero(String.valueOf(cHora.get(Calendar.SECOND)), 2 );
			
			vMens.clear();
			
			if(cab.next()){
				iNumNota = cab.getInt(NF.C_DOC);
				vMatObs = Funcoes.strToVectorSilabas(cab.getString(NF.C_OBSPED), 100);
			}
			
			if (iNumNota==0) 
				sNumNota = "000000";
			else 
				sNumNota = Funcoes.strZero(String.valueOf(iNumNota),6);
			
			for (int i=0; i<4; i++) {
				if (bFat) {
					if (parc.next()) {
						sDuplics[i] = sNumNota + "/" + parc.getInt(NF.C_NPARCITREC);
						sVencs[i] = Funcoes.dateToStrDate(parc.getDate(NF.C_DTVENCTO));
						sVals[i] = Funcoes.strDecimalToStrCurrency(12,2,parc.getString(NF.C_VLRPARC));
					} else {
						bFat = false;
						sDuplics[i] = "";
						sVencs[i] = "";
						sVals[i] = "";
					}
				} else {
					bFat = false;
					sDuplics[i] = "";
					sVencs[i] = "";
					sVals[i] = "";
				}
			}
			 
			
			while (itens.next()) {
				if (bNat) {
					sNat[0] = itens.getString(NF.C_DESCNAT).trim();
					sNat[1] = Funcoes.setMascara(itens.getString(NF.C_CODNAT),"#.##");
					bNat = false;
				}
				if (imp.pRow()==0) {
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow()+1, 0, imp.comprimido());
					if (nf.getTipoNF()==NF.TPNF_ENTRADA)
						imp.say(imp.pRow(), 111, "X");
					else
						imp.say(imp.pRow(), 95, "X");
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  9, sNat[0]);
					imp.say(imp.pRow(), 57, sNat[1]);
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  9, cab.getInt(NF.C_CODEMIT) + " - " + cab.getString(NF.C_RAZEMIT));
					imp.say(imp.pRow(), 92, !cab.getString(NF.C_CPFEMIT).equals("") ? Funcoes.setMascara(cab.getString(NF.C_CPFEMIT),"###.###.###-##") : Funcoes.setMascara(cab.getString(NF.C_CNPJEMIT),"##.###.###/####-##")) ;
					imp.say(imp.pRow(),127, Funcoes.dateToStrDate(cab.getDate(NF.C_DTEMITPED)));
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  9, Funcoes.copy(cab.getString(NF.C_ENDEMIT),0,50).trim()+", "+(!(cab.getInt(NF.C_NUMEMIT)==0) ? Funcoes.copy(""+cab.getInt(NF.C_NUMEMIT),0,6).trim() : "").trim()+" - "+(Funcoes.copy(cab.getString(NF.C_COMPLEMIT),0,9)).trim());
					imp.say(imp.pRow(), 80, Funcoes.copy(cab.getString(NF.C_BAIREMIT),0,15));
					imp.say(imp.pRow(),104, Funcoes.setMascara(cab.getString(NF.C_CEPEMIT),"#####-###"));
					imp.say(imp.pRow(),127, Funcoes.dateToStrDate(cab.getDate(NF.C_DTSAIDA)));
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  9, cab.getString(NF.C_CIDEMIT));
					imp.say(imp.pRow(), 58, (Funcoes.setMascara(cab.getString(NF.C_DDDEMIT) + " ","(####)")) + 
											(Funcoes.setMascara(cab.getString(NF.C_FONEEMIT).trim(),"####-####")));
					imp.say(imp.pRow(), 80, cab.getString(NF.C_UFEMIT));
					imp.say(imp.pRow(), 92, !cab.getString(NF.C_RGEMIT).equals("") ? cab.getString(NF.C_RGEMIT) : cab.getString(NF.C_INSCEMIT));
					imp.say(imp.pRow(),127, sHora);
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					   
					imp.say(imp.pRow(),  9, sDuplics[0]);
					imp.say(imp.pRow(), 24, sVals[0]);
					imp.say(imp.pRow(), 42, sVencs[0]);
					imp.say(imp.pRow(), 60, sDuplics[1]);
					imp.say(imp.pRow(), 76, sVals[1]);
					imp.say(imp.pRow(), 94, sVencs[1]);
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(), 14, sDuplics[2]);
					imp.say(imp.pRow(), 27, sVals[2]);
					imp.say(imp.pRow(), 42, sVencs[2]);
					imp.say(imp.pRow(), 60, sDuplics[3]);
					imp.say(imp.pRow(), 76, sVals[3]);
					imp.say(imp.pRow(), 94, sVencs[3]);
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  9, cab.getString(NF.C_CIDCOBEMIT)+" / "+cab.getString(NF.C_UFCOBEMIT)+" - "+cab.getString(NF.C_ENDCOBEMIT)+" , "+cab.getString(NF.C_NUMCOBEMIT)+ " - "+cab.getString(NF.C_BAIRCOBEMIT));
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  9, Funcoes.doubleToStrCurExtenso(Double.parseDouble(""+itens.getFloat(NF.C_VLRLIQPED)),moeda));
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
				}
				
				imp.say(imp.pRow()+1,0, imp.comprimido());
				imp.say(imp.pRow()+0,  4, Funcoes.alinhaDir(itens.getInt(NF.C_CODPROD),8));
				 
				 
				//Gambs para colocar o lote:
				if ((itens.getDate(NF.C_VENCLOTE) != null) && (!itens.getString(NF.C_CODLOTE).equals(""))) 
					sDescAdic = "  - "+itens.getString(NF.C_CODLOTE).trim();

				sTmp = itens.getString(NF.C_DESCFISC).trim(); 
				//Gambs para colocar arteriscos:
				if (sTmp.length() > 0) {
					int iLinha;
					for (iLinha=0;iLinha<vMens.size();iLinha++) {
						if (((String[])vMens.elementAt(iLinha))[1].equals(sTmp) &&
								((String[])vMens.elementAt(iLinha))[0].indexOf("*") == 0) {
							sDescAdic += " "+((String[])vMens.elementAt(iLinha))[0];
							break;
						}
					}
					if (iLinha==vMens.size()) {
						vMens.add(new String[] {Funcoes.replicate("*",iContaMens++),sTmp});
						sDescAdic += " "+((String[])vMens.elementAt(iLinha))[0];
					}					 	 
				}
					
				sTmp = itens.getString(NF.C_DESCFISC2).trim();
				sClasFisc = Funcoes.copy(itens.getString(NF.C_ORIGFISC),0,1)+Funcoes.copy(itens.getString(NF.C_CODTRATTRIB),0,2);
				
				if (sTmp.length() > 0) {
					int iLinha;
					for (iLinha=0;iLinha<vMens.size();iLinha++) {
						if (((String[])vMens.elementAt(iLinha))[0].equals(sClasFisc))
							break;
					}
					if (iLinha==vMens.size()) {
						vMens.add(new String[] {sClasFisc,sTmp});
					}
				}
				 
				Vector vDesc = Funcoes.strToVectorSilabas((itens.getString(NF.C_OBSITPED).equals("") ? (itens.getString(NF.C_DESCPROD).trim()) : itens.getString(NF.C_OBSITPED))+sDescAdic,49);
				
				
				for (int i=0; i<vDesc.size();i++){
					if (vDesc.elementAt(i)!=null)
						sDesc = ((String)vDesc.elementAt(i));
					else
						sDesc = "";					
					if (i>0)
						imp.say(imp.pRow()+1, 0, imp.comprimido());
					imp.say(imp.pRow(), 15, sDesc);
				}
				 
				sCodfisc = Funcoes.copy(itens.getString(NF.C_CODFISC).trim(),8);         
				if(!sCodfisc.equals("")) {
					for(int i=0;i<vClfiscal.size();i++){
						if(vClfiscal.elementAt(i)!=null){
							if(sCodfisc.equals((String)vClfiscal.elementAt(i))){
								bjatem = true;
								sSigla = ""+(char)(65 + i);
							} else
								bjatem = false;
						}
					}
					if(!bjatem){
						vClfiscal.addElement(sCodfisc);
						sSigla = ""+(char)(64 + vClfiscal.size());
						vSigla.addElement(sSigla + " = " + sCodfisc);
					}
				}     
				            
				imp.say(imp.pRow(), 64, sSigla);
				imp.say(imp.pRow(), 68, Funcoes.copy(itens.getString(NF.C_ORIGFISC),0,1)+Funcoes.copy(itens.getString(NF.C_CODTRATTRIB),0,2));
				imp.say(imp.pRow(), 74, itens.getString(NF.C_CODUNID).substring(0,4));
				imp.say(imp.pRow(), 80, String.valueOf(itens.getFloat(NF.C_QTDITPED)));
				imp.say(imp.pRow(), 92, Funcoes.strDecimalToStrCurrency(8,2,""+((new BigDecimal(itens.getFloat(NF.C_VLRLIQITPED))).divide(new BigDecimal(itens.getFloat(NF.C_QTDITPED)),2,BigDecimal.ROUND_HALF_UP))));
				imp.say(imp.pRow(),103, Funcoes.strDecimalToStrCurrency(13,2,""+itens.getFloat(NF.C_VLRLIQITPED)));
				imp.say(imp.pRow(),119, String.valueOf(itens.getFloat(NF.C_PERCICMSITPED)));
				imp.say(imp.pRow(),125, String.valueOf(itens.getFloat(NF.C_PERCIPIITPED)));
				imp.say(imp.pRow(),128, Funcoes.strDecimalToStrCurrency(7,2,""+itens.getFloat(NF.C_VLRIPIITPED)));
				 
				iItImp++;
				if ((iItImp == itens.getInt(NF.C_CONTAITENS)) || (imp.pRow() > iLinMax)) {
					
					frete.next();
					
					if ((imp.pRow()+vMatObs.size())> iLinMax) {
						Funcoes.mensagemInforma(null,"N�mero de linhas ultrapassa capacidade do formul�rio!");
						imp.fechaGravacao();
						return false;
					}
					if (iItImp == itens.getInt(NF.C_CONTAITENS)) {
						for (int i=0; i<vMatObs.size() ;i++ ) {
							imp.say(imp.pRow()+1,0, imp.comprimido());
							imp.say(imp.pRow(), 14, (vMatObs.elementAt(i)!=null ? (String)vMatObs.elementAt(i) : ""));
						}				
						
						iRow = imp.pRow();
						
						for (int i=0; i<(iLinMax-iRow);i++) 
							imp.say(imp.pRow()+1,0, imp.comprimido());

						iItImp = 0;
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow(),  9, Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRBASEICMSPED)));
						imp.say(imp.pRow(), 36, Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRICMSPED)));
						imp.say(imp.pRow(),117, Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRPRODPED)));
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow(),  9, Funcoes.strDecimalToStrCurrency(20,2,""+frete.getFloat(NF.C_VLRFRETEPED)));
						imp.say(imp.pRow(), 68, Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRADICPED)));
						imp.say(imp.pRow(), 93, Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRIPIPED)));
						imp.say(imp.pRow(),117, Funcoes.strDecimalToStrCurrency(20,2,""+itens.getFloat(NF.C_VLRLIQPED)));
					}
					
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  9, frete.getString(NF.C_RAZTRANSP));
					imp.say(imp.pRow(), 90, frete.getString(NF.C_TIPOFRETE).equals("C") ? "1" : "2");
					imp.say(imp.pRow(), 96, frete.getString(NF.C_PLACAFRETE));
					imp.say(imp.pRow(),112, frete.getString(NF.C_UFFRETE));
					   
					sTipoTran = frete.getString(NF.C_TIPOTRANSP);
					if ( sTipoTran.equals("C") ) 
						imp.say(imp.pRow(),119, Funcoes.setMascara(cab.getString(NF.C_CNPJEMIT),"##.###.###/####-##"));														
					else
						imp.say(imp.pRow(),119, Funcoes.setMascara(frete.getString(NF.C_CNPJTRANSP),"##.###.###/####-##"));			
					   				   
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  9, Funcoes.copy(frete.getString(NF.C_ENDTRANSP),0,42)+"   "+Funcoes.copy(""+frete.getInt(NF.C_NUMTRANSP),0,6));
					imp.say(imp.pRow(), 80, Funcoes.copy(frete.getString(NF.C_CIDTRANSP),0,30));
					imp.say(imp.pRow(),111, frete.getString(NF.C_UFTRANSP));
					
					if ( sTipoTran.equals("C") )
						imp.say(imp.pRow(), 119, cab.getString(NF.C_INSCEMIT));
					else
						imp.say(imp.pRow(), 119, frete.getString(NF.C_INSCTRANSP));						
					
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow(),  9, frete.getString(NF.C_QTDFRETE));
					imp.say(imp.pRow(), 29, frete.getString(NF.C_ESPFRETE));
					imp.say(imp.pRow(), 58, frete.getString(NF.C_MARCAFRETE));
					imp.say(imp.pRow(),109, Funcoes.strDecimalToStrCurrency(5,casasDec,String.valueOf(frete.getString(NF.C_PESOBRUTO))));
					imp.say(imp.pRow(),132, Funcoes.strDecimalToStrCurrency(5,casasDec,String.valueOf(frete.getString(NF.C_PESOLIQ))));
					imp.say(imp.pRow()+1,0, imp.comprimido());
					imp.say(imp.pRow()+1,0, imp.comprimido());
					   
					for(int i=0; i<vSigla.size(); i++){
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow(),  9, (String)vSigla.elementAt(i));
					}
					   
					for(int i=0;i<vMens.size();i++)
						sObs += ((String[])vMens.elementAt(i))[0] + " - " +((String[])vMens.elementAt(i))[1]+"\n";
					
					vObs = Funcoes.strToVectorSilabas(sObs,80);
					   
					for (int i=0;( (i < 20) && (vObs.size()>i) );i++){
						if (vObs.elementAt(i)!=null)
							sObs = ((String)vObs.elementAt(i));
						else
							sObs = "";
						
						imp.say(imp.pRow()+1,0, imp.comprimido());
						imp.say(imp.pRow(),  9, sObs);
					}
					       
					for (int i=imp.pRow(); i<=iLinPag; i++)  
						imp.say(imp.pRow()+1,0, imp.comprimido());
				}
			}
			
			imp.fechaGravacao();
			bRetorno = true;
		} catch ( Exception err ) {
			Funcoes.mensagemErro(null,"Erro ao consultar tabela de Venda!"+err.getMessage());   
			err.printStackTrace();
			bRetorno = false;
		} finally {
			sDescAdic = null;
			sCodfisc = null;
			sClasFisc = null;
			sSigla = null;
			sNumNota = null; 
			sTipoTran = null;
			sObs = null;
			sDesc = null;
			sHora = null;
			sTmp = null;
			sNat = null;
			sVencs = null;
			sVals = null;
			sDuplics = null;
			moeda = null;
			vClfiscal = null;
			vMatObs = null;
			vMens = null;
			vObs = null;
			vSigla = null;
			cHora = null;
			System.gc();
		}
		return bRetorno;
	}
}