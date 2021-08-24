package ru.vstu.AuditorApi.POJO;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;


@Data
public class ResultPointDTO {

  private float quizResult;
  private float srsResult;
  private float modResult;
  private float codestyleResult;
  private float algorithmResult;
  private float protocolResult;
  private float sumResult;

  public static ResultPointDTO mapResultPointDTO(Object[] resultPoint){
    ResultPointDTO resultPointDTO= new ResultPointDTO();
    resultPointDTO.quizResult =(float) resultPoint[0];
    resultPointDTO.srsResult = (float) resultPoint[1];
    resultPointDTO.modResult =(float) resultPoint[2];
    resultPointDTO.codestyleResult =(float) resultPoint[3];
    resultPointDTO.algorithmResult =(float) resultPoint[4];
    resultPointDTO.protocolResult =(float) resultPoint[5];
    resultPointDTO.sumResult= Arrays
            .stream(resultPoint)
            .map(p -> (float)p)
            .reduce(0.0F, (p, total) -> total + p);
    return resultPointDTO;
  }
}
