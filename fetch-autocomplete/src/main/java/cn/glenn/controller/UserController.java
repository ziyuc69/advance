package cn.glenn.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: wangxg
 * @date: 2019-07-20 17:01
 **/

@RestController
@RequestMapping("/user")
public class FetchController {

    @RequestMapping("/list")
    public void fetch() {
        System.out.println("hello");
    }
}
