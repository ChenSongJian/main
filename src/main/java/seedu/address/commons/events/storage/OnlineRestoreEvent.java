package seedu.address.commons.events.storage;

import java.util.Optional;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.UserPrefs;
import seedu.address.storage.OnlineStorage;

//@@author QzSG

/** Indicates a request for online backup*/
public class OnlineRestoreEvent extends BaseEvent {

    public final OnlineStorage.Type target;
    public final UserPrefs.TargetBook targetBook;
    public final String ref;
    public final Optional<String> authToken;

    public OnlineRestoreEvent(OnlineStorage.Type target, UserPrefs.TargetBook targetBook,
                              String ref, Optional<String> authToken) {
        this.target = target;
        this.targetBook = targetBook;
        this.ref = ref;
        this.authToken = authToken;
    }

    @Override
    public String toString() {
        return "Restoring online backup";
    }
}
