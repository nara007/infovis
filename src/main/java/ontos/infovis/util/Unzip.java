package ontos.infovis.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * This is a unzip tool class
 * 
 * @author Ye Song
 * @version 1.0
 * @date 15-6-12
 * @copyright infovis@tu-dresden.de
 */
public class Unzip {

  /**
   * Method unzips a zip file
   * 
   * @param sZipPathFile
   * @param sDestPath
   * @return
   */
  public ArrayList ectract(String sZipPathFile, String sDestPath) {
    ArrayList<String> allFileName = new ArrayList<String>();
    try {
      FileInputStream fins = new FileInputStream(sZipPathFile);
      ZipInputStream zins = new ZipInputStream(fins);
      ZipEntry ze = null;
      byte[] ch = new byte[256];
      while ((ze = zins.getNextEntry()) != null) {
        File zfile = new File(sDestPath + ze.getName());
        File fpath = new File(zfile.getParentFile().getPath());
        if (ze.isDirectory()) {
          if (!zfile.exists())
            zfile.mkdirs();
          zins.closeEntry();
        } else {
          if (!fpath.exists())
            fpath.mkdirs();
          FileOutputStream fouts = new FileOutputStream(zfile);
          int i;
          allFileName.add(zfile.getAbsolutePath());
          while ((i = zins.read(ch)) != -1)
            fouts.write(ch, 0, i);
          zins.closeEntry();
          fouts.close();
        }
      }
      fins.close();
      zins.close();
    } catch (Exception e) {
      System.err.println("Extract error:" + e.getMessage());
    }
    return allFileName;
  }
}
