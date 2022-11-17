package com.hf.op.rest;


import com.hf.common.infrastructure.exception.ServerErrorException;
import com.hf.common.infrastructure.resp.ResponseMsg;
import com.hf.common.infrastructure.util.HttpClientUtilDw;
import com.hf.common.infrastructure.util.StringUtilLocal;
import com.hf.op.infrastructure.config.MinioFSClient;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 前端web
 *
 * @author chensy
 * @function
 * @date 2022/4/6
 */
@RestController
@RequestMapping("v1/file")
@Slf4j
@RefreshScope
public class OpFileController {

  public static final String fileType = ".jpg";

  @Value("${file-save-path}")
  private String fileSavePath;

  @Value("${minio.fileUrl}")
  private String fileUrl;

  public OpFileController() {
  }

  @ApiOperation(value = "文件上传", notes = "无")
  @PostMapping(value = "/uploadFile")
  public ResponseMsg uploadFile(MultipartFile file, HttpServletRequest req) {
    return new ResponseMsg(uploadImage(file, req));
  }

  private String uploadImage(MultipartFile file, HttpServletRequest req) {
    //获取文件的名字
    String originName = file.getOriginalFilename();
    System.out.println("originName:" + originName);
    if (StringUtilLocal.isEmpty(originName)) {
      return null;
    }
    //判断文件类型
//    if (!originName.endsWith(".jpg")) {
//      result.put("status", "error");
//      result.put("msg", "文件类型不对");
//      return result;
//    }
    //给上传的文件新建目录
    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy.MM.dd/");
    String format = sdf.format(new Date());
    String realPath = fileSavePath + format;
    System.out.println("realPath:" + realPath);
    //若目录不存在则创建目录
    File folder = new File(realPath);
    if (!folder.exists()) {
      folder.mkdirs();
    }
    //给上传文件取新的名字，避免重名
    String newName = UUID.randomUUID().toString().replace("-", "") + "-" + originName;
    try {
      //生成文件，folder为文件目录，newName为文件名
      file.transferTo(new File(folder, newName));
      //生成返回给前端的url
      String path = "/images" + format + newName;
      String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + path;
      System.out.println("url:" + url);
      System.out.println("path:" + path);
      //返回URL
      return path;
    } catch (IOException e) {
      e.printStackTrace();
      log.error("上传失败", e);
    }
    return null;
  }

  public String download(String urlStringObj) {
    String path = null;
    OutputStream os = null;
    InputStream is = null;
    try {
      String urlString = urlStringObj;
      // 构造URL
      URL url = new URL(urlString);
      // 打开连接
      URLConnection con = url.openConnection();
      //设置请求超时为5s
      con.setConnectTimeout(5 * 1000);
      // 输入流
      is = con.getInputStream();
      // 1K的数据缓冲
      byte[] bs = new byte[1024];
      // 读取到的数据长度
      int len;
      // 路径
      String filename = urlString.substring(urlString.lastIndexOf("/"));
      SimpleDateFormat sdf = new SimpleDateFormat("/yyyy.MM.dd/");
//      String format = sdf.format(new Date());
      String realPath = fileSavePath ;
      System.out.println("realPath:" + realPath);
      // 输出的文件流
      File sf = new File(realPath);
      if (!sf.exists()) {
        sf.mkdirs();
      }
      os = new FileOutputStream(sf.getPath() + filename);
      // 开始读取
      while ((len = is.read(bs)) != -1) {
        os.write(bs, 0, len);
      }
      // 完毕，关闭所有链接
      os.close();
      is.close();
      path = "/images" + filename.replace("/", "");
      System.out.println(path);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (null != os) {
          os.close();
        }
        if (null != is) {
          is.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return path;
  }

  /**
   * @return 应用信息
   */
  @RequestMapping(value = "getImgUrl", method = RequestMethod.GET)
  @ResponseBody
  public ResponseMsg getImgUrl(@RequestParam(name = "actNo") String actNo, HttpServletRequest req) {
    String url = HttpClientUtilDw.getData(actNo);
    if (StringUtilLocal.isEmpty(url)) {
      throw new ServerErrorException(999, "未找到图片,请手动上传");
    }
//    String path = download(url);
    String path = downloadAndUpdate(url,actNo);
    String url1 = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + path;
    System.out.println("url:" + url1);
    Map<String, Object> map = new HashMap<>();
    map.put("url", path);
    return new ResponseMsg().setData(map);
  }



  public String downloadAndUpdate(String urlStringObj,String  actNo) {
    String path = null;
    OutputStream os = null;
    InputStream is = null;
    try {
      String urlString = urlStringObj;
      // 构造URL
      URL url = new URL(urlString);
      // 打开连接
      URLConnection con = url.openConnection();
      //设置请求超时为5s
      con.setConnectTimeout(5 * 1000);
//      String filename = urlString.substring(urlString.lastIndexOf("/"));
      // 输入流
//      is = con.getInputStream();
       path = MinioFSClient.uploadFileNormal(actNo + fileType, con.getInputStream());

//      // 1K的数据缓冲
//      byte[] bs = new byte[1024];
//      // 读取到的数据长度
//      int len;
//      // 路径
//      String filename = urlString.substring(urlString.lastIndexOf("/"));
//      SimpleDateFormat sdf = new SimpleDateFormat("/yyyy.MM.dd/");
//      String format = sdf.format(new Date());
//      String realPath = fileSavePath + format;
//      System.out.println("realPath:" + realPath);
//      // 输出的文件流
//      File sf = new File(realPath);
//      if (!sf.exists()) {
//        sf.mkdirs();
//      }
//      os = new FileOutputStream(sf.getPath() + filename);
//      // 开始读取
//      while ((len = is.read(bs)) != -1) {
//        os.write(bs, 0, len);
//      }
//      // 完毕，关闭所有链接
//      os.close();
//      is.close();
//      path = "/images" + format + filename.replace("/", "");
      System.out.println(path);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (null != os) {
          os.close();
        }
        if (null != is) {
          is.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return path;
  }


  @ApiOperation(value = "文件上传", notes = "无")
  @PostMapping(value = "/uploadFileMinio")
  public ResponseMsg uploadFileMinio( MultipartFile file,
      @ApiParam(value = "类型：GUNI全局唯一，CUST自定义静态资源")
      @RequestParam(name = "actNo", required = false) String actNo)
//      ,@RequestParam(name = "fileName", required = false) String fileName)
  {
    try {
      String fileName = StringUtilLocal.isNotEmpty(actNo) ? actNo + fileType : System.currentTimeMillis() + fileType;
      String filePath = MinioFSClient.uploadFileNormal(fileName, file.getInputStream());
      String url = fileUrl + filePath;
      log.info(filePath);
      log.info(url);
      return new ResponseMsg(filePath);
    } catch (Exception e) {
      log.error(ExceptionUtils.getStackTrace(e));
    }
    return new ResponseMsg();
  }

}
