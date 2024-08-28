package com.sanku.dynamodemo.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.sanku.dynamodemo.model.TestEntity;

@EnableScan
public interface SampleRepository extends CrudRepository<TestEntity, String> {
}