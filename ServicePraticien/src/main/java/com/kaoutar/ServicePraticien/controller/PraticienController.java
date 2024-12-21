package com.kaoutar.ServicePraticien.controller;

import com.kaoutar.ServicePraticien.model.Praticien;
import com.kaoutar.ServicePraticien.service.PraticienService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/praticiens")
@Tag(name = "Praticien", description = "API de gestion des praticiens")
public class PraticienController {

    @Autowired
    private PraticienService praticienService;

    @GetMapping
    @Operation(summary = "Récupérer tous les praticiens", description = "Retourne la liste de tous les praticiens")
    @ApiResponse(responseCode = "200", description = "Liste des praticiens récupérée avec succès")
    public List<Praticien> getAllPraticiens() {
        return praticienService.getAllPraticiens();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un praticien par son ID", description = "Retourne un praticien selon l'ID fourni")
    @ApiResponse(responseCode = "200", description = "Praticien trouvé")
    @ApiResponse(responseCode = "404", description = "Praticien non trouvé")
    public ResponseEntity<Praticien> getPraticienById(@PathVariable Long id) {
        return praticienService.getPraticienById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau praticien", description = "Crée un nouveau praticien avec les informations fournies")
    @ApiResponse(responseCode = "200", description = "Praticien créé avec succès")
    public Praticien createPraticien(@RequestBody Praticien praticien) {
        return praticienService.savePraticien(praticien);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un praticien", description = "Met à jour les informations d'un praticien existant")
    @ApiResponse(responseCode = "200", description = "Praticien mis à jour avec succès")
    @ApiResponse(responseCode = "404", description = "Praticien non trouvé")
    public ResponseEntity<Praticien> updatePraticien(@PathVariable Long id, @RequestBody Praticien praticien) {
        Praticien updatedPraticien = praticienService.updatePraticien(id, praticien);
        return updatedPraticien != null ? 
                ResponseEntity.ok(updatedPraticien) : 
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un praticien", description = "Supprime un praticien selon l'ID fourni")
    @ApiResponse(responseCode = "200", description = "Praticien supprimé avec succès")
    public ResponseEntity<Void> deletePraticien(@PathVariable Long id) {
        praticienService.deletePraticien(id);
        return ResponseEntity.ok().build();
    }
} 