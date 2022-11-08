package com.test.op;

import java.util.List;
import lombok.Data;

/**
 * Created by wpq on 2021/12/01.
 */
@Data
public class Node {

  private int id;
  private int pid;
  private String name;
  private List<Node> child;

  public Node(int id, int pid) {
    this.id = id;
    this.pid = pid;
    this.name = "测试:" + pid + ":" + id;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getPid() {
    return pid;
  }

  public void setPid(int pid) {
    this.pid = pid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Node> getChild() {
    return child;
  }

  public void setChild(List<Node> child) {
    this.child = child;
  }

}
