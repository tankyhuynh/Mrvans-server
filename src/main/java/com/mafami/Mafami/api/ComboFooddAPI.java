package com.mafami.Mafami.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.mafami.Mafami.Entity.ComboFoodEntity;
import com.mafami.Mafami.Service.ComboFoodService;
import com.mafami.Mafami.Utils.FileUtils;
import com.mafami.Mafami.model.PriceModel;

@RestController
@RequestMapping("/api/combo")
public class ComboFooddAPI {

	@Autowired
	private ComboFoodService comboFoodService;
	
	@Autowired
	private FileUtils fileUtils;
	
	@GetMapping
	public List<ComboFoodEntity> getAll() {
		return comboFoodService.findAll();
	}
	
	@GetMapping("/page/{numberOfPage}")
	public Page<ComboFoodEntity> getAllByNumberOfPage(@PathVariable("numberOfPage") int numberOfPage) {
		return comboFoodService.findAllByPage(numberOfPage);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ComboFoodEntity> getOneById(@PathVariable String id) {
		ComboFoodEntity comboFoodEntity = comboFoodService.findOneById(id);
		if (comboFoodEntity != null) {
			return ResponseEntity.ok(comboFoodService.findOneById(id));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PostMapping
	public ComboFoodEntity saveOne(@RequestBody ComboFoodEntity userEntity) {
		return comboFoodService.save(userEntity);
	}
	
	@PostMapping("/all")
	public ResponseEntity<String> saveAll(@PathVariable("site") String site, @RequestBody List<ComboFoodEntity> comboFoodEntities) {
		List<PriceModel> prices = new ArrayList<>();
		for (ComboFoodEntity entity : comboFoodEntities) {
			try {
				String URL = fileUtils.decoder(entity.getThumbnail(), "ImageAPI");
				entity.setThumbnail(URL);
			} catch (Exception e) {
				System.out.println("Exception: " + e);
			}
			comboFoodService.save(entity);
		}
		return ResponseEntity.ok("OK");
	}

	@PutMapping("/{id}")
	public ResponseEntity<ComboFoodEntity> saveOneById(@PathVariable String id, @RequestBody(required = false) ComboFoodEntity newEntity) {
		ComboFoodEntity oldEntity = comboFoodService.findOneById(id);
		newEntity.setId(id);
		if (oldEntity != null)
			return ResponseEntity.ok(comboFoodService.save(newEntity));

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@DeleteMapping("/{id}")
	public void deleteOneById(@PathVariable String id) {
		comboFoodService.delete(id);
	}

	@DeleteMapping("/all")
	public void deleteAll() {
		comboFoodService.deleteAll();
	}
	
}