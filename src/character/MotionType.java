package character;

public enum MotionType {
    // Man & Enemy 공통 모션
    IDLE, DEAD, DAMAGE,

    // Man 공격 모션
    MAN_ATTACK01, MAN_ATTACK02, MAN_ATTACK03, MAN_ATTACK04,

    // Man(Sword) 공격 모션
    MAN_SWORD_IDLE, MAN_SWORD_ATTACK01, MAN_SWORD_ATTACK02, MAN_SWORD_ATTACK03, MAN_SWORD_ATTACK04,

    // Enemy 공격 모션
    ENEMY_ATTACK01, ENEMY_ATTACK02,

    // Enemy 방어 모션(Skeleton 한정)
    SHIELD
}
