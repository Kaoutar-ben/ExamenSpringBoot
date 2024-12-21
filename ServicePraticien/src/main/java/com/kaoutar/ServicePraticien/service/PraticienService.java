package com.kaoutar.ServicePraticien.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kaoutar.ServicePraticien.model.Praticien;

@Service
public class PraticienService {
    private final List<Praticien> praticiens = new ArrayList<>();
    private Long nextId = 1L;

    public PraticienService() {
        // Ajout de quelques praticiens de test
        praticiens.add(new Praticien(nextId++, "Martin", "Paul", "Cardiologie", "paul.martin@email.com", "0123456789"));
        praticiens.add(new Praticien(nextId++, "Dubois", "Marie", "Pédiatrie", "marie.dubois@email.com", "0234567890"));
        praticiens.add(new Praticien(nextId++, "Bernard", "Jean", "Dermatologie", "jean.bernard@email.com", "0345678901"));
    }

    public List<Praticien> getAllPraticiens() {
        return praticiens;
    }

    public Optional<Praticien> getPraticienById(Long id) {
        return praticiens.stream()
                .filter(praticien -> praticien.getId().equals(id))
                .findFirst();
    }

    public Praticien savePraticien(Praticien praticien) {
        if (praticien.getId() == null) {
            praticien.setId(nextId++);
        }
        praticiens.add(praticien);
        return praticien;
    }

    public void deletePraticien(Long id) {
        praticiens.removeIf(praticien -> praticien.getId().equals(id));
    }

    public Praticien updatePraticien(Long id, Praticien praticien) {
        for (int i = 0; i < praticiens.size(); i++) {
            if (praticiens.get(i).getId().equals(id)) {
                praticien.setId(id);
                praticiens.set(i, praticien);
                return praticien;
            }
        }
        return null;
    }
} 