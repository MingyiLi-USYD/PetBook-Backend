package usyd.mingyi.springcloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.pojo.PostImage;
import usyd.mingyi.springcloud.service.PostImageService;

import java.util.List;

@RestController
@Slf4j
public class PostImageController {
    @Autowired
    PostImageService postImageService;


    @GetMapping("/images/post/{postId}")
    public R<List<PostImage>> getImagesByPostId(@PathVariable("postId") Long postId){
        List<PostImage> postImages = postImageService.
                list(new LambdaQueryWrapper<PostImage>().eq(PostImage::getPostId, postId));
        return R.success(postImages);
    }

    @DeleteMapping("/images/post/{postId}")
    public R<String> deleteImagesByPostId(@PathVariable("postId") Long postId){
        postImageService.
                remove(new LambdaQueryWrapper<PostImage>().eq(PostImage::getPostId, postId));
        return R.success("Success");
    }


}
