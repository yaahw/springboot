package com.ynding.springboot.web.controller;

import com.ynding.springboot.entity.Book;
import com.ynding.springboot.o.bo.GQuery;
import com.ynding.springboot.o.bo.ResponseBean;
import com.ynding.springboot.web.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import java.util.List;
import java.util.Map;

/**
 * @author ynding
 *
 */
@Slf4j
@RestController
@RequestMapping("/book")
@Api(value="Book",tags={"Book-Controller"})
@CrossOrigin(origins = "*")
public class BookController {

	private final BookService bookService;

	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@PostMapping("/insert")
	@ApiOperation(value = "添加书本", produces = "application/json")
    public ResponseBean insert(@RequestBody Book book){

		bookService.save(book);
		return ResponseBean.ok(1);
	}

	@GetMapping("/list")
	@ApiOperation(value = "查询列表", produces = "application/json")
	public ResponseBean findList(@RequestParam Map<String, Object> params){

	    GQuery query = new GQuery(params);
	    List<Book> books = bookService.findList(query);

		return ResponseBean.ok(books);
	}

	@GetMapping("/page")
	@ApiOperation(value = "分页查询", produces = "application/json")
	public ResponseBean findPage(@RequestParam Map<String, Object> params){
		GQuery query = new GQuery(params);
		Page<Book> page = bookService.pageList(query);

		return ResponseBean.ok(page);
	}

}
