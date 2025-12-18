<div align="center">
 
 # 객체지향언어2 기말 미니 프로젝트

### 게임 구도
<img width="500" height="500" alt="제목 없음 (1000 x 1000 px)의 사본의 사본 (1)" src="https://github.com/user-attachments/assets/70c6e8e2-3d2a-4090-b753-1c80a493c40f" />


주인공은 4마리의 적과 대치한다. 각각의 적은 서로 다른 HP , 공격력을 가지고 있다. 

모드는 각각 4개의 모드가 있고, 각각 연습 모드, 쉬움 모드, 보통 모드, 어려움 모드로 나뉘어져 있다.

점수 판정 → 단어를 맞추면 Man이 Monster을 공격한다. 

반대로 단어가 바닥에 떨어지거나 맞추지 못하면 Monster가 Man을 공격한다. 각각의 상황은 공격력에 따른 점수 +a, -a가 반영된다.

&nbsp;

<img width="500" height="500" alt="man" src="https://github.com/user-attachments/assets/86615e11-e355-453a-a4b5-2f20e8f53c81" />


주인공은 게임 시작 시 체력 100, 공격력 1, 무기 없음 상태로 시작한다.

단어를 맞출 때마다 네 가지 공격 모션 중 하나가 랜덤으로 재생된다.

&nbsp;

<img width="500" height="500" alt="swordman" src="https://github.com/user-attachments/assets/33a64c86-e841-4207-b9bd-96f7a51a2d6d" />


점수를 3점 획득하면 오른쪽 하단 패널에 무기 버튼이 활성화된다. 

무기를 장착하면 공격력이 1에서 3으로 증가하며, 단어를 맞출 때마다 네 가지 공격 모션 중 하나가 이전과 동일하게 랜덤으로 재생된다.

&nbsp;

## 게임 모드

<img width="500" height="500" alt="scarecrow" src="https://github.com/user-attachments/assets/1296b601-4040-443e-a085-190e7a111574" />


Scarecrow는 사용자의 설정에 따라 체력과 단어 낙하속도를 원하는대로 지정할 수 있다. 

또한 이 캐릭터는 공격을 하지 않는다. 오로지 사용자의 연습을 위해서 존재하는 모드이다. 

아침에 출현한다.

&nbsp;

<img width="500" height="500" alt="mushroom" src="https://github.com/user-attachments/assets/14184f09-8051-41c5-89f4-bd821fac66cb" />


Mushroom은 체력 5, 공격력 5을 가진 약한 몬스터이다. 

낮에 출현한다.

&nbsp;

<img width="500" height="500" alt="wolf" src="https://github.com/user-attachments/assets/b75b8246-659c-41df-bb5a-8f9abf01df33" />


Wolf는 체력 30, 공격력 10을 가진 약하지 않은 몬스터이다. 

밤에 출현한다.

&nbsp;

<img width="500" height="500" alt="reaper" src="https://github.com/user-attachments/assets/77fac7b7-70b5-4454-bff4-1ffd1cedc53f" />


Reaper는 체력 60, 공격력 20을 가진 강한 몬스터이다.

주인공이 단어를 오타를 내서 맞히지 못한 경우에는 고유의 스킬을 사용한다.

해당 스킬은 모든 단어를 0.2초간 급낙하를 시킨다.

주인공이 단어를 놓친 경우에는 일반 공격을 한다.

밤에 출현한다.

&nbsp;

## 아이템

<img width="500" height="500" alt="swordpotion" src="https://github.com/user-attachments/assets/e6c42920-8cb0-49df-a975-d77c85f4f15f" />


위에 나와있듯이, 3점 이상의 점수를 획득하면 무기 버튼이 활성화된다. 

이를 누르면 주인공은 무기를 장착하고 싸울 수 있게 된다.

포션은 모든 스테이지마다 기본적으로 3개씩 주어진다.

포션을 사용할때마다 체력이 20씩 증가한다.

&nbsp;

<img width="500" height="500" alt="timestop" src="https://github.com/user-attachments/assets/bed39321-7f71-49a4-b397-a1f33be71e29" />


모든 스테이지마다 시간을 멈출 수 있는 3번의 기회가 주어진다.

이때 단어를 맞춰서 단어를 없앨 수 있다.

3번의 기회를 다 쓰면, 더이상 사용하지 못한다.

&nbsp;

## 게임 실행

### [로그인 화면]
<img width="1920" height="1080" alt="login" src="https://github.com/user-attachments/assets/ddc10cd6-d18f-4ba4-9de8-b50651933dc6" />
&nbsp;

### [회원가입 화면]
<img width="1920" height="1080" alt="signup" src="https://github.com/user-attachments/assets/2320e695-f583-4d25-b0b9-ed5f0a42e227" />
&nbsp;

### [회원가입 성공]
<img width="1920" height="1080" alt="signup02" src="https://github.com/user-attachments/assets/f62d61f2-74b0-4e4f-82dc-7d5ecdf0a767" />
&nbsp;

### [메뉴 화면]
<img width="1920" height="1080" alt="snapshot" src="https://github.com/user-attachments/assets/d818a9ed-a407-44f0-8d57-ad86522d5017" />
&nbsp;

### [모드 선택 화면]
<img width="1920" height="1080" alt="snapshot1" src="https://github.com/user-attachments/assets/a080d823-af04-4715-aafa-e503e06f9705" />
&nbsp;

### [랭킹 화면]
<img width="1920" height="1080" alt="snapshot2" src="https://github.com/user-attachments/assets/319b5d4a-6410-45d1-8fe9-2d0d4e3fc3bf" />
&nbsp;

### [단어장 설정 화면]
<img width="1920" height="1080" alt="snapshot3" src="https://github.com/user-attachments/assets/ab8ec075-0310-455f-ace6-e23fd94d670e" />
&nbsp;

### [연습 모드 게임 설정]
<img width="1920" height="1080" alt="snapshot5" src="https://github.com/user-attachments/assets/87bb26b3-cede-429a-9e8c-050a175c8b57" />
&nbsp;

### [게임 시작]
<img width="1920" height="1080" alt="snapshot6" src="https://github.com/user-attachments/assets/6dd86d0b-c54f-42dd-ae05-a886d1162e81" />
&nbsp;

### [주인공 기본 공격]
<img width="1920" height="1080" alt="snapshot7" src="https://github.com/user-attachments/assets/b600d371-52c9-4a45-a50e-7cbe2cf3167c" />
&nbsp;

### [주인공 무기 공격]
<img width="1920" height="1080" alt="snapshot8" src="https://github.com/user-attachments/assets/e0483763-64ff-4342-971b-0bf3db44f0dd" />
&nbsp;

### [연습 모드 클리어]
<img width="1920" height="1080" alt="snapshot9" src="https://github.com/user-attachments/assets/d63ade58-c980-45cc-95c4-6ab281aa5897" />
&nbsp;

### [쉬움 모드]
<img width="1920" height="1080" alt="snapshot10" src="https://github.com/user-attachments/assets/22f7ddad-9e93-4f45-a469-b6aba733d066" />
&nbsp;

### [Mushroom의 공격]
<img width="1920" height="1080" alt="snapshot11" src="https://github.com/user-attachments/assets/83bdb6d9-295a-458a-a671-02e2010b8666" />
&nbsp;

### [보통 모드]
<img width="1920" height="1080" alt="snapshot12" src="https://github.com/user-attachments/assets/4201ddef-3650-45c2-88b7-320d97ad4344" />
&nbsp;

### [어려움 모드]
<img width="1920" height="1080" alt="snapshot13" src="https://github.com/user-attachments/assets/e74a8ff3-fbaa-4422-8129-8735d922f3ff" />
&nbsp;

### [단어 못맞춤 -> 몬스터 스킬 사용 -> 단어 0.2초 급낙하] (어려움 모드 한정)
<img width="1920" height="1080" alt="snapshot14" src="https://github.com/user-attachments/assets/f05e6f1d-a6a5-4a9a-bc43-8fd2ee65667b" />
&nbsp;

### [몬스터 공격]
<img width="1920" height="1080" alt="snapshot15" src="https://github.com/user-attachments/assets/c9766c67-4317-4131-941b-dcab7990b665" />
&nbsp;

### [포션 사용 - 체력 20 증가]
<img width="1920" height="1080" alt="snapshot17" src="https://github.com/user-attachments/assets/1e94221a-ba5a-4cc9-b65b-96df257edaf7" />
&nbsp;

### [타임 스탑 아이템 사용 - 시간이 3초동안 멈춤. 단어 맞춰서 없앨 수 있음]
<img width="1920" height="1080" alt="snapshot16" src="https://github.com/user-attachments/assets/873ae21f-b058-453a-9caf-922b4260b471" />
&nbsp;

### [주인공 패배]
<img width="1920" height="1080" alt="snapshot18" src="https://github.com/user-attachments/assets/27c56362-0b43-44b6-952b-28b21eeaa7b4" />
&nbsp;

### [로그아웃]
<img width="1920" height="1080" alt="snapshot19" src="https://github.com/user-attachments/assets/76455482-dfa8-422b-bcd9-518dda284ba8" />

---

### 다이어그램

<img width="5200" height="2856" alt="ㅇㅇㅇ" src="https://github.com/user-attachments/assets/0aa6e7b8-f830-4623-8611-9af469e598cd" />

---

### 게임 에셋 출처:

배경화면: https://assetstore.unity.com/packages/2d/environments/free-4k-parallax-vector-backgrounds-299874

주인공: https://assetstore.unity.com/packages/2d/characters/pixel-prototype-player-sprites-221542

Scarecrow : https://assetstore.unity.com/packages/2d/characters/straw-training-dummy-pixel-art-323464

Mushroom : https://assetstore.unity.com/packages/2d/characters/monsters-creatures-fantasy-167949

Wolf : https://assetstore.unity.com/packages/2d/characters/dark-wolf-2d-animation-284086

Reaper : https://assetstore.unity.com/packages/2d/characters/bringer-of-death-free-195719

</div>
