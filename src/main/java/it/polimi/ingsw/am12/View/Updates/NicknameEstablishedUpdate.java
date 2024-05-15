package it.polimi.ingsw.am12.View.Updates;

import it.polimi.ingsw.am12.ViewModelUpdater;

/**
 * Update to handle the registration of a player nickname
 */
public class NicknameEstablishedUpdate implements Update{
    String nickname;

    /**
     * Class constructor
     * @param nickname the registered nickname
     */
    public NicknameEstablishedUpdate(String nickname){
        this.nickname = nickname;
    }

    /**
     * Update the ViewModel
     * @param viewModelUpdater the ViewModel to update
     */
    @Override
    public void executeUpdate(ViewModelUpdater viewModelUpdater) {
        viewModelUpdater.nicknameEstablishedUpdate(nickname);
    }

    /**
     * Getter of the nickname
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }
}
