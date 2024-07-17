package com.dag.productservice.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class V0 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
