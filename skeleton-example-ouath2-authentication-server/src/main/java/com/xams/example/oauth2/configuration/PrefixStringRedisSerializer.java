package com.xams.example.oauth2.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class PrefixStringRedisSerializer extends StringRedisSerializer {

  @Value("${spring.redis.key-prefix:}")
  private String prefix;

  public PrefixStringRedisSerializer() {
    this(StandardCharsets.UTF_8);
  }

  public PrefixStringRedisSerializer(Charset charset) {
    super(charset);
  }

  @Override
  public String deserialize(@Nullable byte[] bytes) {
    String prefixString = super.deserialize(bytes);
    return StringUtils.isEmpty(prefixString) ? null : prefixString.replace(prefix, "");
  }

  @Override
  public byte[] serialize(@Nullable String string) {
    String prefixString = prefix + string;
    return StringUtils.isEmpty(prefixString) ? null : super.serialize(prefixString);
  }
}
