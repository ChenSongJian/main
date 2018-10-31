package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyTaskBook;

/**
 * A class to access TaskBook data stored as an xml file on the hard disk.
 */
public class XmlTaskBookStorage implements TaskBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskBookStorage.class);

    private Path filePath;

    public XmlTaskBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTaskBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException {
        return readTaskBook(filePath);
    }

    /**
     * Similar to {@link #readTaskBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaskBook> readTaskBook(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("TaskBook file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableTaskBook xmlTaskBook = XmlFileStorage.loadTaskFromSaveFile(filePath);
        try {
            return Optional.of(xmlTaskBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTaskBook(ReadOnlyTaskBook taskBook) throws IOException {
        saveTaskBook(taskBook, filePath);
    }

    /**
     * Similar to {@link #saveTaskBook(ReadOnlyTaskBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTaskBook(ReadOnlyTaskBook taskBook, Path filePath) throws IOException {
        requireNonNull(taskBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableTaskBook(taskBook));
    }

    @Override
    public void backupTaskBook(ReadOnlyTaskBook taskBook, Path backupFilePath) throws IOException {
        saveTaskBook(taskBook, backupFilePath);
    }

}
