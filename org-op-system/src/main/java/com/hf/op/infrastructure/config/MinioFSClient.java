package com.hf.op.infrastructure.config;

import com.hf.common.infrastructure.resp.DFSConst;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.SetBucketPolicyArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import jodd.net.MimeTypes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by ck on 2020/8/25.
 * <p/>
 */
@Component
@RefreshScope
@Slf4j
public class MinioFSClient {

  public static final char PATH_SEP = '/';

  private static MinioClient minioClient;

  //minio服务Ip地址
  @Value("${minio.serverUrl}")
  private String endpoint;
  //minio接入用户名
  @Value("${minio.assessKey}")
  private String accessKey;
  //minio接入密码
  @Value("${minio.secretKey}")
  private String secretKey;

//  public MinioFSClient() {
//    minioClient = MinioClient.builder().endpoint("https://natt.yimed.cn:11902")
//        .credentials("remote", "remote1234")
//        .build();
//  }

  private static String ruleBasicBucketExpireLifeCycle() {
    return
        "<LifecycleConfiguration>\n" +
            "  <Rule>\n" +
            "    <ID>expire-bucket</ID>\n" +
            "    <Prefix></Prefix>\n" +
            "    <Status>Enabled</Status>\n" +
            "    <Expiration>\n" +
            "      <Days>1</Days>\n" +
            "    </Expiration>\n" +
            "  </Rule>\n" +
            "</LifecycleConfiguration>";
  }

  private static String ruleBasicBucketReadOnlyPolicy(String bucketName) {
    return String.format(
        "{\n" +
            "  \"Version\": \"2012-10-17\",\n" +
            "  \"Statement\": [{\n" +
            "    \"Effect\": \"Allow\",\n" +
            "    \"Principal\": {\n" +
            "      \"AWS\": [\"*\"]\n" +
            "    },\n" +
            "    \"Action\": [\"s3:GetObject\"],\n" +
            "    \"Resource\": [\"arn:aws:s3:::%s/*\"]\n" +
            "  }]\n" +
            "}", bucketName);
  }

  //  public static String uploadFile(String bucket, String path, String fileName, InputStream stream) {
//    return MinioFSClient.uploadFile(bucket, path, fileName, stream, null);
//  }
  public static String uploadFile(String fileName, InputStream stream) throws Exception {
    fileName = System.currentTimeMillis() + "-" + fileName;
    return MinioFSClient
        .uploadFile(DFSConst.Bucket.STATIC, DFSConst.Path.DEFAULT, fileName, stream, null);
  }

  public static String uploadFileNormal(String fileName, InputStream stream) throws Exception {
    return MinioFSClient
        .uploadFile(DFSConst.Bucket.STATIC, DFSConst.Path.DEFAULT, fileName, stream, null);
  }

  public static String uploadFile(
      String bucket, String path, String fileName, InputStream stream, String contentType)
      throws Exception {
    Assert.notNull(bucket, "bucket must not be null!");
    Assert.notNull(path, "path must not be null!");
    Assert.notNull(fileName, "fileName must not be null!");
    Assert.notNull(stream, "data must not be null!");
    createBucketIfNotExists(bucket);
    ObjectWriteResponse objectWriteResponse = minioClient.putObject(
        PutObjectArgs.builder().bucket(bucket)
            .stream(stream, stream.available(), -1)
            .object(path + PATH_SEP + fileName)
            .contentType(contentType == null ?
                MimeTypes.getMimeType(MinioFSClient.getFileNameExtension(fileName)) : contentType)
            .build());
    String target =
        PATH_SEP + objectWriteResponse.bucket() + PATH_SEP + objectWriteResponse.object();
    log.debug("Upload file to minio success! {}", target);
    return target;
  }

  public static void createBucketIfNotExists(String bucket) {
    try {
      boolean existsBucket = minioClient
          .bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
      if (!existsBucket) {
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
        minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucket)
            .config(ruleBasicBucketReadOnlyPolicy(bucket)).build());
      }
    } catch (Exception e) {
      log.error("Unable to create bucket, exception: {}", ExceptionUtils.getStackTrace(e));
    }
  }

  public static String getFileNameExtension(String fileName) {
    return fileName.substring(fileName.lastIndexOf(".") + 1);
  }

  /**
   * 以流的形式获取一个文件对象（断点下载）
   *
   * @param bucketName 存储桶名称
   * @param objectName 存储桶里的对象名称
   * @param offset 起始字节的位置
   * @param length 要读取的长度 (可选，如果无值则代表读到文件结尾)
   */
  public static InputStream getObject(String bucketName, String objectName, Long offset,
      Long length)
      throws Exception {
    return minioClient.getObject(GetObjectArgs.builder()
        .bucket(bucketName)
        .object(objectName)
        .offset(offset)
        .length(length)
        .build());
  }

  public static StatObjectResponse getObjectInfo(String bucketName, String objectName)
      throws Exception {
    StatObjectResponse statObjectResponse = minioClient
        .statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
    return statObjectResponse;
  }

  @PostConstruct
  public void init() {
    try {
      minioClient = MinioClient.builder().endpoint(endpoint)
          .credentials(accessKey, secretKey)
          .build();
    } catch (Exception e) {
      e.printStackTrace();
      log.error("minioncliet init error: ", e.fillInStackTrace());
    }
  }
//  public InputStream getObject(String bucket, String path, String fileName)
//      throws IOException,
//      InvalidKeyException, InvalidResponseException, InsufficientDataException,
//      NoSuchAlgorithmException, ServerException, InternalException,
//      XmlParserException, InvalidBucketNameException, ErrorResponseException {
//    return minioClient.getObject(GetObjectArgs.builder()
//        .bucket(bucket)
//        .object(path + PATH_SEP + fileName)
//        .build());
//  }
//
//  public String uploadFile(String path, String fileName, InputStream stream)
//      throws IOException,
//      InvalidResponseException, RegionConflictException, InvalidKeyException,
//      NoSuchAlgorithmException, ServerException, ErrorResponseException,
//      XmlParserException, InvalidBucketNameException, InsufficientDataException, InternalException {
//    return MinioFSClient.uploadFile(DFSConst.Bucket.DEFAULT, path, fileName, stream, null);
//  }
//
//  public String uploadTempFile(String path, String fileName, InputStream stream)
//      throws IOException,
//      InvalidKeyException, InvalidResponseException, InsufficientDataException,
//      NoSuchAlgorithmException, ServerException, InternalException, XmlParserException,
//      InvalidBucketNameException, ErrorResponseException, RegionConflictException {
//    return this.uploadTempFile(DFSConst.Bucket.TEMP, path, fileName, stream, null);
//  }
//
//  private String uploadTempFile(String bucket, String path, String fileName, InputStream stream,
//      String contentType)
//      throws IOException,
//      InvalidKeyException, InvalidResponseException, InsufficientDataException,
//      NoSuchAlgorithmException, ServerException, InternalException, XmlParserException,
//      InvalidBucketNameException, ErrorResponseException, RegionConflictException {
//    Assert.notNull(bucket, "bucket must not be null!");
//    Assert.notNull(path, "path must not be null!");
//    Assert.notNull(fileName, "fileName must not be null!");
//    Assert.notNull(stream, "data must not be null!");
//    createExpireBucketIfNotExists(bucket);
//    ObjectWriteResponse objectWriteResponse = minioClient.putObject(
//        PutObjectArgs.builder().bucket(bucket)
//            .stream(stream, stream.available(), -1)
//            .object(path + PATH_SEP + fileName)
//            .contentType(contentType == null ?
//                MimeTypes.getMimeType(getFileNameExtension(fileName)) : contentType)
//            .build());
//    String target =
//        PATH_SEP + objectWriteResponse.bucket() + PATH_SEP + objectWriteResponse.object();
//    log.debug("Upload file to minio success! {}", target);
//    return target;
//  }

//  public String getPresignedObjectPutUrl(String bucket, String path, String fileName, int expire,
//      TimeUnit timeUnit)
//      throws IOException,
//      InvalidResponseException, InvalidKeyException, InvalidExpiresRangeException,
//      ServerException, ErrorResponseException, NoSuchAlgorithmException, XmlParserException,
//      InvalidBucketNameException, InsufficientDataException, InternalException {
//    return this.getPresignedObjectPutUrl(false, bucket, path, fileName, expire, timeUnit, null);
//  }
/*

  public String getPresignedPostPolicyUrlSimple(String bucket) {
    return this.getPresignedPostPolicyUrlSimple(false, bucket);
  }

  public String getPresignedPostPolicyUrlSimplePrivate(String bucket) {
    return this.getPresignedPostPolicyUrlSimple(true, bucket);
  }

  public String getPresignedPostPolicyUrlSimple(boolean isPrivate, String bucket) {
    HttpUrl httpUrl = HttpUrl.parse(minioConfig.getEndpoint());
    if (httpUrl != null) {
      if (isPrivate) {
        createPrivateBucketIfNotExists(bucket);
      } else {
        createBucketIfNotExists(bucket);
      }
      HttpUrl.Builder urlBuilder = httpUrl.newBuilder();
      urlBuilder.addEncodedPathSegment(S3Escaper.encode(bucket));
      return urlBuilder.build().toString();
    }
    return null;
  }
*/

//  public String getPresignedObjectPutUrlPrivate(String bucket, String path, String fileName,
//      int expire,
//      TimeUnit timeUnit) throws IOException,
//      InvalidResponseException, InvalidKeyException, InvalidExpiresRangeException,
//      ServerException, ErrorResponseException, NoSuchAlgorithmException, XmlParserException,
//      InvalidBucketNameException, InsufficientDataException, InternalException {
//    return this.getPresignedObjectPutUrl(true, bucket, path, fileName, expire, timeUnit, null);
//  }

//  public String getPresignedObjectPutUrl(boolean isPrivate, String bucket, String path,
//      String fileName, int expire,
//      TimeUnit timeUnit, String contentType)
//      throws IOException,
//      InvalidKeyException, InvalidResponseException, InsufficientDataException,
//      InvalidExpiresRangeException, ServerException, InternalException,
//      NoSuchAlgorithmException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
//    Assert.notNull(bucket, "bucket must not be null!");
//    Assert.notNull(path, "path must not be null!");
//    Assert.notNull(fileName, "fileName must not be null!");
//    if (isPrivate) {
//      createPrivateBucketIfNotExists(bucket);
//    } else {
//      createBucketIfNotExists(bucket);
//    }
//    Map<String, String> reqParams = new HashMap<>();
//    reqParams.put("response-content-type", contentType == null ?
//        MimeTypes.getMimeType(getFileNameExtension(fileName)) : contentType);
//    return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
//        .method(Method.PUT)
//        .bucket(bucket)
//        .object(path + PATH_SEP + fileName)
//        .expiry(expire, timeUnit)
//        .extraQueryParams(reqParams)
//        .build());
//  }
//
//  public Map<String, String> presignedPostPolicy(String bucket, String path, String fileName,
//      long expireSeconds, String contentType)
//      throws IOException, InvalidKeyException, InvalidResponseException,
//      InsufficientDataException, InvalidExpiresRangeException, ServerException, InternalException,
//      NoSuchAlgorithmException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
//    PostPolicy policy = new PostPolicy(bucket,
//        path + PATH_SEP + fileName,
//        ZonedDateTime.now().plusSeconds(expireSeconds));
//    policy.setContentType(contentType == null ?
//        MimeTypes.getMimeType(getFileNameExtension(fileName)) : contentType);
//    policy.setSuccessActionStatus(HttpStatus.SC_OK);
//    return minioClient.presignedPostPolicy(policy);
//  }

//  public void createPrivateBucketIfNotExists(String bucket) {
//    try {
//      boolean existsBucket = minioClient
//          .bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
//      if (!existsBucket) {
//        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
//      }
//    } catch (Exception e) {
//      log.error("Unable to create bucket, exception: {}", ExceptionUtils.getStackTrace(e));
//    }
//  }

//  private void createExpireBucketIfNotExists(String bucket) {
//    try {
//      boolean existsBucket = minioClient
//          .bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
//      if (!existsBucket) {
//        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
//        minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucket)
//            .config(ruleBasicBucketReadOnlyPolicy(bucket)).build());
//        minioClient.setBucketLifeCycle(SetBucketLifeCycleArgs.builder().bucket(bucket)
//            .config(ruleBasicBucketExpireLifeCycle()).build());
//      }
//    } catch (Exception e) {
//      log.error("Unable to create bucket, exception: {}", ExceptionUtils.getStackTrace(e));
//    }
//  }
}
