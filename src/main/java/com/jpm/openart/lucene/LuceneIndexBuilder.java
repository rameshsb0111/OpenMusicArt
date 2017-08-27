package com.jpm.openart.lucene;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Arrays;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class LuceneIndexBuilder {

	private static Field[] fields = new Field[] { new Field("title", "", Field.Store.NO, Field.Index.ANALYZED_NO_NORMS),
			new Field("genre", "", Field.Store.NO, Field.Index.ANALYZED_NO_NORMS),
			new Field("artist", "", Field.Store.NO, Field.Index.ANALYZED_NO_NORMS),
			new Field("fileName", "", Field.Store.NO, Field.Index.ANALYZED_NO_NORMS),
			new Field("format", "", Field.Store.NO, Field.Index.ANALYZED_NO_NORMS),
			new Field("duration", "", Field.Store.NO, Field.Index.ANALYZED_NO_NORMS),
			new Field("id", "", Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS) };

	private final String fileName;;

	public LuceneIndexBuilder(String fileName) {
		this.fileName = fileName;
	}

	public RAMDirectory buildIndex() throws Exception {

		RAMDirectory ramDirectory = new RAMDirectory();
		Document document = new Document();

		BufferedReader reader = new BufferedReader(new FileReader(this.fileName));

		IndexWriter indexWriter = new IndexWriter(ramDirectory,
				new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] data = getSongData(line);
				setFieldData(data, fields);
				indexWriter.addDocument(document);

			}
		} finally {
			indexWriter.close();
		}

		return ramDirectory;
	}

	private String[] getSongData(String line) {
		return line.split("|");
	}

	private void setFieldData(String[] data, Field[] fields) {
		int index = 0;
		for (Field field : fields) {
			field.setValue(data[index++]);
		}
	}

	private void addFieldToDocument(final Document doc) {
		Arrays.stream(fields).forEach(f -> doc.add(f));
	}

}
