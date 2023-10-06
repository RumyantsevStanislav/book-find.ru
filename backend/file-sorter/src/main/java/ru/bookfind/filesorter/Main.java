package ru.bookfind.filesorter;

import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    private static final String COVERS_ROOT_PATH = "F:\\covers";
    private static final String OLD_DIRECTORY_NAME_PREFIX = "BookImages";
    private static final int MAX_IMAGE_IN_FOLDER = 10000;

    public static void main(String[] args) throws IOException {
        Path coversPath = Path.of(COVERS_ROOT_PATH);
        try (DirectoryStream<Path> oldImgDirectories = Files.newDirectoryStream(coversPath,
                (dir) -> dir.getFileName()
                        .toString()
                        .startsWith(OLD_DIRECTORY_NAME_PREFIX))) {
            for (Path directory : oldImgDirectories) {
                moveFilesFromDirectory(directory);
            }
        }
    }

    /**
     * Moving all files from directory.
     *
     * @param directory directory, contains movable files.
     * @throws IOException
     */
    private static void moveFilesFromDirectory(Path directory) throws IOException {
        try (DirectoryStream<Path> imgPaths = Files.newDirectoryStream(directory)) {
            for (Path imgPath : imgPaths) {
                moveFile(imgPath);
            }
        }
    }

    /**
     * Moving file from directory.
     *
     * @param imgPath - image path.
     * @throws IOException
     */
    private static void moveFile(Path imgPath) throws IOException {
        Path targetPath = createTargetFolder(imgPath);
        Files.move(imgPath, targetPath.resolve("cover.jpg"));
    }

    /**
     * Returns target directory to moving file. Creating if it does not exist.
     *
     * @param imgPath путь к файлу.
     * @return target directory.
     * @throws IOException
     */
    private static Path createTargetFolder(Path imgPath) throws IOException {
        String fileName = FilenameUtils.removeExtension(imgPath.getFileName().toString());
        String targetFolder = String.valueOf(Integer.parseInt(fileName) / MAX_IMAGE_IN_FOLDER);
        Path targetPath = imgPath.getParent().getParent().resolve(targetFolder).resolve(fileName);
        if (!Files.exists(targetPath)) {
            Files.createDirectories(targetPath);
        }
        return targetPath;
    }

}