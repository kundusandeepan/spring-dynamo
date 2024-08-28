package com.sanku.dynamodemo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanku.dynamodemo.model.TestEntity;
import com.sanku.dynamodemo.repository.SampleRepository;

@Service
public class SampleService {

    @Autowired
    private SampleRepository sampleRepository;

    public TestEntity save(TestEntity entity) {
        return sampleRepository.save(entity);
    }

    public Optional<TestEntity> findById(String id) {
        return sampleRepository.findById(id);
    }

    public Iterable<TestEntity> findAll() {
        return sampleRepository.findAll();
    }

    public void deleteById(String id) {
        sampleRepository.deleteById(id);
    }
}