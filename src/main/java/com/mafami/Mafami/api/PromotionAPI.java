package com.mafami.Mafami.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mafami.Mafami.Entity.PromotionEntity;
import com.mafami.Mafami.Service.PromotionService;

@RestController
@RequestMapping("/api/promotion")
public class PromotionAPI {

	@Autowired
	private PromotionService promotionService;

	@GetMapping
	public List<PromotionEntity> getAll() {
		return promotionService.getAll();
	}

	@GetMapping("/{site}/{id}")
	public ResponseEntity<PromotionEntity> getOneById(@PathVariable("id") String id) {
		PromotionEntity promotionEntity = promotionService.findOneById(id);
		if (promotionEntity != null) {
			return ResponseEntity.ok(promotionService.findOneById(id));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PostMapping
	public PromotionEntity saveOneBySite(@PathVariable String site, @RequestBody PromotionEntity promotionEntity) {
		return promotionService.save(promotionEntity);
	}

	@PutMapping("/{site}/{id}")
	public ResponseEntity<PromotionEntity> saveOneById(@PathVariable("site") String site, @PathVariable("id") String id,
			@RequestBody PromotionEntity newEntity) {
		PromotionEntity oldEntity = promotionService.findOneById(id);
		newEntity.setId(id);
		if (oldEntity != null)
			return ResponseEntity.ok(promotionService.save(newEntity));

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@DeleteMapping("/{id}")
	public void deleteOneById(@PathVariable String id) {
		promotionService.delete(id);
	}

	@DeleteMapping("/all")
	public void deleteAll() {
		promotionService.deleteAll();
	}

}