package com.hace.prove.hace.repository;

import com.hace.prove.hace.beans.NameAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NomeAddressRepository extends JpaRepository<NameAddress,String> {
}
