package com.test.op;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @function
 * @date 2021/11/29
 */
public class JiJin {

  public JiJin(BigDecimal dfMoney, BigDecimal ratio) {
    this.dfMoney = dfMoney;
    this.ratio = ratio;
  }

  BigDecimal dfMoney = new BigDecimal(0);

  BigDecimal ratio = new BigDecimal(0);

  public BigDecimal getDfMoney() {
    return dfMoney;
  }

  public void setDfMoney(BigDecimal dfMoney) {
    this.dfMoney = dfMoney;
  }

  public BigDecimal getRatio() {
    return ratio;
  }

  public void setRatio(BigDecimal ratio) {
    this.ratio = ratio;
  }

  public BigDecimal calculateIncome() {
    return dfMoney.multiply(ratio);
  }

  public static void countJiJin() {
    List<JiJin> jiJins = new ArrayList<>();
    JiJin dfxJiJin = new JiJin(new BigDecimal("8079.19"), new BigDecimal("1.83"));
    jiJins.add(dfxJiJin);
    JiJin dfaJiJin = new JiJin(new BigDecimal("2023.33"), new BigDecimal("2.35"));
    jiJins.add(dfaJiJin);
    JiJin thgfJiJin = new JiJin(new BigDecimal("6886.79"), new BigDecimal("1.13"));
    jiJins.add(thgfJiJin);
    JiJin jszzJiJin = new JiJin(new BigDecimal("9061.50"), new BigDecimal("0.44"));
    jiJins.add(jszzJiJin);
    JiJin zsbjJiJin = new JiJin(new BigDecimal("7441.44"), new BigDecimal("0.73"));
    jiJins.add(zsbjJiJin);
    JiJin yhJiJin = new JiJin(new BigDecimal("9663.56"), new BigDecimal("1.41"));
    jiJins.add(yhJiJin);
    JiJin jxJiJin = new JiJin(new BigDecimal("2105.42"), new BigDecimal("1.38"));
    jiJins.add(jxJiJin);
    JiJin hbqsJiJin = new JiJin(new BigDecimal("7960.84"), new BigDecimal("-0.89"));
    jiJins.add(hbqsJiJin);
    JiJin wjJiJin = new JiJin(new BigDecimal("3019.7"), new BigDecimal("1.31"));
    jiJins.add(wjJiJin);

    BigDecimal total = new BigDecimal(0);
    for (int i = 0; i < jiJins.size(); i++) {
      JiJin item = jiJins.get(i);
      total = total.add(item.calculateIncome());
    }

    System.out.println("total:" + total);

  }

  public static void main(String[] args) {
    countJiJin();
  }
}
