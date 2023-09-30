package usyd.mingyi.springcloud.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import usyd.mingyi.common.common.CustomException;
import usyd.mingyi.common.common.R;
import usyd.mingyi.common.pojo.PostImage;
import usyd.mingyi.springcloud.service.PostImageService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class PostImageController {
    @Autowired
    PostImageService postImageService;




    @GetMapping("/images/post/{postId}")
    public R<List<PostImage>> getImagesByPostId(@PathVariable("postId") Long postId) {
        List<PostImage> postImages = postImageService.
                list(new LambdaQueryWrapper<PostImage>().eq(PostImage::getPostId, postId));
        return R.success(postImages);
    }

    @PostMapping("/images/post")
    public R<String> savePostImages(@RequestBody @Valid List<PostImage> postImages) {
        if (postImageService.saveBatch(postImages)) {
            return R.success("保存成功");
        }else {
            throw new CustomException("保存0个");
        }
    }

    @DeleteMapping("/images/post/{postId}")
    public R<String> deleteImagesByPostId(@PathVariable("postId") Long postId) {
        postImageService.
                remove(new LambdaQueryWrapper<PostImage>().eq(PostImage::getPostId, postId));
        return R.success("Success");
    }


}
