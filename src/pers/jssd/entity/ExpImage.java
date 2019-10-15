package pers.jssd.entity;


import java.io.Serializable;
import java.util.Objects;

public class ExpImage implements Serializable {

    private int imgId;
    private int expId;
    private String realName;
    private String fileName;
    private String fileType;

    @Override
    public String toString() {
        return "ExpImage{" +
                "imgId=" + imgId +
                ", expId=" + expId +
                ", realName='" + realName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpImage expImage = (ExpImage) o;
        return imgId == expImage.imgId &&
                expId == expImage.expId &&
                Objects.equals(realName, expImage.realName) &&
                Objects.equals(fileName, expImage.fileName) &&
                Objects.equals(fileType, expImage.fileType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imgId, expId, realName, fileName, fileType);
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getExpId() {
        return expId;
    }

    public void setExpId(int expId) {
        this.expId = expId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public ExpImage(int imgId, int expId, String realName, String fileName, String fileType) {
        this.imgId = imgId;
        this.expId = expId;
        this.realName = realName;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public ExpImage() {
    }
}
