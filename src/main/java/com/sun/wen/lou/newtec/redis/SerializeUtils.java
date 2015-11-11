package com.sun.wen.lou.newtec.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtils
{
  public static byte[] serialize(Object object)
  {
    ObjectOutputStream oos = null;
    ByteArrayOutputStream baos = null;
    try
    {
      baos = new ByteArrayOutputStream();
      oos = new ObjectOutputStream(baos);
      oos.writeObject(object);
      byte[] bytes = baos.toByteArray();
      return bytes;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (oos != null) {
          oos.close();
        }
        if (baos != null)
          baos.close();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  public static Object unserialize(byte[] bytes)
  {
    ByteArrayInputStream bais = null;
    ObjectInputStream ois = null;
    try
    {
      bais = new ByteArrayInputStream(bytes);
      ois = new ObjectInputStream(bais);
      return ois.readObject();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (ois != null) {
          ois.close();
        }
        if (bais != null)
          bais.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    return null;
  }
}