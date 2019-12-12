import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;

import java.sql.Timestamp;

public class PostOperaciones_InsertarDataBase extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbMessage inMessage = inAssembly.getMessage();
		MbMessage inMessageLocalEnvironment = inAssembly.getLocalEnvironment();
	    MbMessage outMessage = new MbMessage(inMessage);
	    MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly,outMessage);
		
		try {
 
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	        Connection conn = getJDBCType4Connection("{Policies}:DB2Policy", JDBC_TransactionType.MB_TRANSACTION_AUTO);
	        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);	        
			MbElement dataElement = inMessageLocalEnvironment.getRootElement();
	        
	        String numero1 = dataElement.getFirstElementByPath("DataEntrada/numero1").getValue().toString();
	        String numero2 = dataElement.getFirstElementByPath("DataEntrada/numero2").getValue().toString();
	        String operador = dataElement.getFirstElementByPath("DataEntrada/operador").getValue().toString();
	        
	        stmt.executeUpdate("INSERT INTO OPER01 (HORA_TRANS, OPERANDO1, OPERANDO2, OPERADOR) VALUES('"
	        		+ timestamp + "',"
	        		+ "" + numero1 + ","
	        		+ "" + numero2 + ","
	        		+ "'" + operador + "'"
       		+")");
	        	        	        
		} catch (MbException e) {
			// Re-throw to allow Broker handling of MbException
			throw e;
		} catch (RuntimeException e) {
			// Re-throw to allow Broker handling of RuntimeException
			throw e;
		} catch (SQLException sqx) {
			// Consider replacing Exception with type(s) thrown by user code
			// Example handling ensures all exceptions are re-thrown to be handled in the flow
			throw new MbUserException(this, "evaluate()", "", "", sqx.toString(), null);
		}
		out.propagate(outAssembly);
	}


}
