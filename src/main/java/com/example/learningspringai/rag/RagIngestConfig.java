package com.example.learningspringai.rag;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
public class RagIngestConfig {

    @Value("classpath:/documents/resume.pdf")
    private Resource resume;

    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel).build();
    }

    @Bean
    ApplicationRunner ingestResume(VectorStore vectorStore) {
        return args ->  {
            // 1. Extract: read PDF page by page
            PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(resume);
            List<Document> rawDocs = pdfReader.get();

            // 2. Transform: split into token-sized chunks for embedding
            TokenTextSplitter splitter = TokenTextSplitter.builder().build();
            List<Document> chunks = splitter.apply(rawDocs);

            // 3. Load: embed + store
            vectorStore.add(chunks);
        };
    }

}
