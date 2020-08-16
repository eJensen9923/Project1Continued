package trmstesting;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dao.DocumentDAO;
import dao.DocumentDAOImpl;
import models.Doc;
import models.Transaction;

public class DocumentDAOTest {
	
	/*
	 * public List<String> getDocuments(Transaction t);
	public Boolean addDocument(Transaction t, String url);
	public Boolean deleteDocument(String url);*/
	
	@Test
	void documentDAO() {
		DocumentDAO dd = new DocumentDAOImpl();
		
		Doc doc = new Doc(null, 0);
		doc.settID(99);
		doc.setUrl("TEST");
		
		Assertions.assertEquals(99, doc.gettID());
		Assertions.assertEquals("TEST", doc.getUrl());
		
		Transaction t = new Transaction();
		t.settID(doc.gettID());
		
		dd.addDocument(t, doc.getUrl());
		
		List<String> urls = dd.getDocuments(t);
		for(String url : urls)
			Assertions.assertFalse(url == null);
		
		dd.deleteDocument(doc.getUrl());
		
		urls = dd.getDocuments(t);
		for(String url: urls)
			Assertions.assertFalse(url == doc.getUrl());
		
		Transaction fail = new Transaction();
		dd.addDocument(fail, "This shouldn't record");
		
		
		
	}//ssh -i C:\JAVADEV\2007jwaKey.pem ec2-user@18.190.26.135

}
