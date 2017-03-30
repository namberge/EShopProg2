package risk.common.entities.missions;

/**
 * Own at least 24 provinces
 */
public class TwentyFourProvincesMission extends Mission {
    public TwentyFourProvincesMission(){
        this.description = "Conquer at least 24 provinces!";
    }

    @Override
    public boolean isComplete() { return player.getProvinces().size() == 24; }
}
