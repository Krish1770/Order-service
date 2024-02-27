package com.example.orderservice.repository;

import com.example.orderservice.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ItemRepository extends JpaRepository<Items,Long>
{

    Optional<List<Items>> findByItemIdIn(Set<Long> itemIdList);
}
