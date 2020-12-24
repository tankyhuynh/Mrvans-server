package com.mafami.Mafami.api;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mafami.Mafami.Entity.CategoryEntity;
import com.mafami.Mafami.Entity.LogEntity;
import com.mafami.Mafami.Service.CategoryService;
import com.mafami.Mafami.Service.LogService;

/**
* @author root {8:39:53 AM}:
 * @version Creation time: Oct 10, 2020 8:39:53 AM
 * Class Description
*/
/**
 * @author tankyhuynh
 *
 */
@RestController
@RequestMapping("/api/category")
public class CategoryAPI {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private LogService logService;

	@GetMapping
	public ResponseEntity<List<CategoryEntity>> getAll() {
		return ResponseEntity.ok(categoryService.getAll());
	}
	
	@GetMapping("/{site}")
	public ResponseEntity<List<CategoryEntity>> getAllBySite(@PathVariable("site") String site) {
		return ResponseEntity.ok(categoryService.getAllBySite(site));
	}
	
	@GetMapping("/{site}/{id}")
	public ResponseEntity<CategoryEntity> getById(@PathVariable("id") String id) {
		return ResponseEntity.ok(categoryService.getOneById(id));
	}

	

	@PostMapping("/{site}")
	public ResponseEntity<CategoryEntity> saveOne(@PathVariable("site") String site, @RequestBody CategoryEntity categoryEntity) throws Exception {
		categoryEntity.setSite(site);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("Etc/GMT0"));
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sf.setTimeZone(TimeZone.getTimeZone("Etc/GMT-7"));
		
		LogEntity logEntity = new LogEntity();
		logEntity.setIcon("https://img.icons8.com/ios-filled/64/000000/information.png");
		String content = "Admin" + " đã thêm danh mục " + categoryEntity.getName()+ " lúc " + ( df.parse(sf.format(Calendar.getInstance().getTime())) ) + " vào danh mục của " + site;
		
		logEntity.setContent(content);
		logService.save(logEntity);
		
		return ResponseEntity.ok(categoryService.save(categoryEntity));
	}
	
	@PostMapping("/{site}/all")
	public ResponseEntity<String> saveAll(@PathVariable("site") String site, @RequestBody List<CategoryEntity> categoryEntity) {
		for (CategoryEntity entity : categoryEntity) {
			entity.setSite(site);
			categoryService.save(entity);
		}
		return ResponseEntity.ok("OK");
	}

	@PutMapping("/{site}/{id}")
	public ResponseEntity<CategoryEntity> update(@PathVariable("site") String site, @PathVariable("id") String id,
			@RequestBody CategoryEntity newEntity) throws Exception {
		CategoryEntity oldEntity = categoryService.getOneById(id);
		newEntity.setId(id);
		newEntity.setSite(site);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("Etc/GMT0"));
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sf.setTimeZone(TimeZone.getTimeZone("Etc/GMT-7"));
		
		LogEntity logEntity = new LogEntity();
		logEntity.setIcon("https://img.icons8.com/ios-filled/64/000000/information.png");
		String content = "Admin" + " đã sửa thông tin danh mục " + newEntity.getName() + " lúc " +( df.parse(sf.format(( Calendar.getInstance().getTime())) ) ) + " trong danh mục của " + site;
		
		logEntity.setContent(content);
		logService.save(logEntity);
		
		return ResponseEntity.ok(categoryService.save(newEntity));
	}
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable("id") String id, @RequestBody String reason) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("Etc/GMT0"));
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sf.setTimeZone(TimeZone.getTimeZone("Etc/GMT-7"));
		
		LogEntity logEntity = new LogEntity();
		logEntity.setIcon("https://img.icons8.com/ios-filled/64/000000/information.png");
		String content = "Admin " + " đã xóa danh mục " + id + " lúc " +  ( df.parse(sf.format(( Calendar.getInstance().getTime())) ) ) +" với lý do " + reason;
		logEntity.setContent(content);
		logService.save(logEntity);
		
		categoryService.delete(id);
	}
	
	@DeleteMapping("/{site}/all")
	public void deleteAllBySite(@PathVariable String site) {
		categoryService.deleteAllBySite(site);
	}
	
	@DeleteMapping("/all")
	public void deleteAll() {
		categoryService.deleteAll();
	}
	
	
	
	
	

}
