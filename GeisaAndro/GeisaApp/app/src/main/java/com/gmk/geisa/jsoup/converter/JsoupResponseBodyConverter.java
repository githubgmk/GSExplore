package com.gmk.geisa.jsoup.converter;

import com.gmk.geisa.jsoup.jsoup.ElementAdapter;

import org.jsoup.nodes.Document;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class JsoupResponseBodyConverter<T> implements Converter<ResponseBody, T> {
  private final ElementAdapter<T> adapter;

  public JsoupResponseBodyConverter(ElementAdapter<T> adapter) {
    this.adapter = adapter;
  }

  @Override
  public T convert(ResponseBody value) throws IOException {
    Document document = JsoupDocumentConverter.INSTANCE.convert(value);
    return adapter.fromElement(document);
  }
}
