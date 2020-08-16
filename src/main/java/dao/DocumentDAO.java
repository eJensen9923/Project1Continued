package dao;

import java.util.List;

import models.Transaction;

public interface DocumentDAO {
	public List<String> getDocuments(Transaction t);
	public Boolean addDocument(Transaction t, String url);
	public Boolean deleteDocument(String url);

}
