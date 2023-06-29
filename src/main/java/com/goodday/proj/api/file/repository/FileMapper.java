package com.goodday.proj.api.file.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper extends FileRepository {
    @Override
    int deleteFile(String filename);
}
