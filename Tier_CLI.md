# TierForge CLI 設計書

## 1. コンセプトシート

| 記入項目 | 内容 |
|---|---|
| **アプリ名** | CLI Tier |
| **コンセプト** | ゲーム・音楽・食べ物・服・ガジェットなど、あらゆる対象を自由に登録し、自分で作成した評価基準や比較結果からTier表を作成できる汎用CLIアプリ |
| **作ろうと思った理由** | よくあるティア表は評価理由が残りにくい。そこで、評価対象・評価基準・重み付けを自由に設定し、数値評価から自動でTierを算出できるツールを作りたいと考えた。また、CLI版ではJava・DB・SQL・オブジェクト指向を使い、後期にはSpring BootでWebアプリへ発展させる。 ドパガキすぎてティア表系コンテンツ好きすぎるから。|

---

## 2. ペルソナ

| 項目 | 記入内容 |
|---|---|
| **名前** | 山田 焼き鳥 |
| **年齢** | 17歳 |
| **職業** | 高校生 |
| **趣味** | ない |
| **困っていること** | 好きなものをランキング化したいとき、感覚だけでTierを決めると理由が曖昧になり、あとから評価を見直しにくい |
| **利用目的** | 比較したい対象と評価基準を自由に登録し、自分の評価を整理したTier表を作りたい |

---

## 3. ユーザーストーリー

| No. | ユーザーストーリー |
|---|---|
| **1** | Tier表を作りたい利用者として、比較したいアイテムを自由に登録したい。なぜなら、ジャンルに縛られず好きな対象を評価したいから。 |
| **2** | Tier表を作りたい利用者として、自分で評価基準を作成したい。なぜなら、比較する対象によって重視するポイントが違うから。 |
| **3** | Tier表を作りたい利用者として、各アイテムを評価し、自動でTierを算出したい。なぜなら、感覚だけでなく評価内容をもとに順位を決めたいから。 |
| **4** | Tier表を作りたい利用者として、登録内容をあとから修正・削除したい。なぜなら、評価や対象をあとから見直すことがあるから。 |
| **5** | Tier表を作りたい利用者として、作成したTier表を保存して再度読み込みたい。なぜなら、アプリを終了したあともデータを残したいから。 |

---

## 4. 機能一覧

| No. | 機能名 | 概要 |
|---|---|---|
| **1** | ボード作成 | 「好きな曲」「スニーカー」「ラーメン」など、Tier表のテーマを作成する |
| **2** | アイテム登録 | Tier表で比較する対象を登録する |
| **3** | アイテム一覧表示 | 登録済みのアイテムを一覧表示する |
| **4** | アイテム更新 | アイテム名・説明・評価などを変更する |
| **5** | アイテム削除 | 不要なアイテムを削除する |
| **6** | 評価基準作成 | 「デザイン」「価格」「味」など任意の評価項目と重みを設定する |
| **7** | 評価入力 | 各アイテムに対して評価基準ごとの点数を入力する |
| **8** | 自動Tier生成 | 評価点と重み付けから総合スコアを計算し、Tierを自動判定する |
| **9** | 検索・並び替え | 名前検索、総合スコア順、Tier順などで表示する |
| **10** | DB保存・読込 | PostgreSQLへボード・アイテム・評価データを保存し、次回起動時にも利用できるようにする |

### 必須CRUDとの対応

| CRUD | TierForgeでの処理 |
|---|---|
| **Create** | アイテム・ボード・評価基準を登録 |
| **Read** | 登録済みデータ・Tier表を一覧表示 |
| **Update** | アイテム名・評価値・重みなどを変更 |
| **Delete** | 不要なアイテム・評価基準を削除 |

---

## 5. Tier自動算出

各評価基準に対して、0～100点で評価する。

例：

```text
Board : スニーカー

評価基準
デザイン       40%
合わせやすさ   25%
履き心地       20%
価格           15%
```

アイテムごとに評価する。

```text
MIHARA BLAKEY

デザイン       : 95
合わせやすさ   : 85
履き心地       : 65
価格           : 55
```

重み付き平均を計算する。

```text
95 × 0.40
85 × 0.25
65 × 0.20
55 × 0.15

TOTAL = 80.50
```

### Tier判定

| 総合スコア | Tier |
|---|---|
| 90以上 | S |
| 80以上 | A |
| 70以上 | B |
| 60以上 | C |
| 60未満 | D |

---

## 6. データ設計

### Board

| 項目名 | 型 | 内容 |
|---|---|---|
| **id** | int | ボードID |
| **name** | String | Tier表の名前 |
| **description** | String | Tier表の説明 |
| **createdAt** | LocalDateTime | 作成日時 |

### Item

| 項目名 | 型 | 内容 |
|---|---|---|
| **id** | int | アイテムID |
| **boardId** | int | 所属するボードID |
| **name** | String | アイテム名 |
| **description** | String | メモ・説明 |
| **totalScore** | double | 総合評価点 |
| **tier** | Tier | S～DのTier |

### Criterion

| 項目名 | 型 | 内容 |
|---|---|---|
| **id** | int | 評価基準ID |
| **boardId** | int | 所属するボードID |
| **name** | String | 評価基準名 |
| **weight** | double | 評価に使用する重み |

### Evaluation

| 項目名 | 型 | 内容 |
|---|---|---|
| **id** | int | 評価ID |
| **itemId** | int | 評価対象のアイテムID |
| **criterionId** | int | 評価基準ID |
| **score** | double | 0～100の評価点 |

---

## 7. データベース設計

CLIアプリから **JDBC** を利用して **PostgreSQL** へ接続する。

```text
TierForge
   ↓
Service
   ↓
Repository
   ↓
JDBC
   ↓
PostgreSQL
```

### テーブル構成

```text
boards
├── id
├── name
├── description
└── created_at

items
├── id
├── board_id
├── name
├── description
├── total_score
└── tier

criteria
├── id
├── board_id
├── name
└── weight

evaluations
├── id
├── item_id
├── criterion_id
└── score
```

### リレーション

```text
Board 1 ───── * Item
  │
  └───── * Criterion

Item 1 ───── * Evaluation * ───── 1 Criterion
```

---

## 8. 使用技術

| 技術 | 使用目的 |
|---|---|
| **Java 17** | アプリ本体 |
| **Scanner** | CLI入力 |
| **ArrayList / HashMap** | 一時的なデータ処理 |
| **Stream API** | 検索・絞り込み |
| **Comparator** | スコア・Tierなどの並び替え |
| **Enum** | S・A・B・C・DのTier管理 |
| **JDBC** | JavaからDBへ接続 |
| **PostgreSQL** | データ永続化 |
| **PostgreSQL JDBC Driver** | PostgreSQL接続用外部ライブラリ |
| **Jackson** | 将来的なJSON Import / Export |
| **Git / GitHub** | ソースコード管理 |

---

## 9. クラス設計

| クラス名 | 役割 |
|---|---|
| **Main** | プログラム開始・メニュー制御 |
| **Board** | Tierボードのデータを保持 |
| **Item** | 評価対象のデータを保持 |
| **Criterion** | 評価基準と重みを保持 |
| **Evaluation** | アイテムごとの評価点を保持 |
| **Tier** | Tierを表すEnum |
| **BoardService** | ボードに関する処理 |
| **ItemService** | アイテムのCRUD処理 |
| **EvaluationService** | 評価登録・更新・削除 |
| **TierService** | 総合スコア計算とTier判定 |
| **BoardRepository** | BoardテーブルへのDB操作 |
| **ItemRepository** | ItemテーブルへのDB操作 |
| **CriterionRepository** | CriterionテーブルへのDB操作 |
| **EvaluationRepository** | EvaluationテーブルへのDB操作 |
| **DatabaseManager** | PostgreSQL接続管理 |
| **InputUtil** | Scanner入力・入力チェック |

---

## 10. 簡易クラス構成

```text
Main
 │
 ├── BoardService
 │     └── BoardRepository
 │
 ├── ItemService
 │     └── ItemRepository
 │
 ├── EvaluationService
 │     ├── CriterionRepository
 │     └── EvaluationRepository
 │
 └── TierService

Repository
    ↓
DatabaseManager
    ↓
PostgreSQL

Model
├── Board
├── Item
├── Criterion
├── Evaluation
└── Tier
```

---

## 11. 推奨パッケージ構成

```text
src
├── main
│   └── Main.java
├── model
│   ├── Board.java
│   ├── Item.java
│   ├── Criterion.java
│   ├── Evaluation.java
│   └── Tier.java
├── service
│   ├── BoardService.java
│   ├── ItemService.java
│   ├── EvaluationService.java
│   └── TierService.java
├── repository
│   ├── BoardRepository.java
│   ├── ItemRepository.java
│   ├── CriterionRepository.java
│   └── EvaluationRepository.java
├── db
│   └── DatabaseManager.java
└── util
    └── InputUtil.java
```

---

## 12. CLI画面イメージ

```text
========================================
               TIER FORGE
========================================

1. ボード作成
2. ボード一覧
3. アイテム登録
4. アイテム一覧
5. アイテム更新
6. アイテム削除
7. 評価基準設定
8. アイテム評価
9. Tier自動生成
10. Tier表表示
0. 終了

番号を入力してください：
```

### Tier表表示例

```text
========================================
         スニーカー Tier List
========================================

[S]
Rick Owens Ramones        92.50

[A]
MIHARA BLAKEY             84.25
New Balance 990           81.70

[B]
Air Force 1               76.30

[C]
Converse All Star         63.80
```

---

## 13. フローチャート

```text
開始
  ↓
PostgreSQL接続
  ↓
メニュー表示
  ↓
番号入力
  │
  ├─ 1 → ボード作成
  ├─ 2 → ボード一覧
  ├─ 3 → アイテム登録
  ├─ 4 → アイテム一覧
  ├─ 5 → アイテム更新
  ├─ 6 → アイテム削除
  ├─ 7 → 評価基準設定
  ├─ 8 → アイテム評価
  ├─ 9 → スコア計算
  │          ↓
  │       Tier自動判定
  │          ↓
  │       DBへ保存
  ├─ 10 → Tier表表示
  └─ 0 → DB接続終了
             ↓
            終了
```

---

## 14. Spring Bootへの発展

CLI版：

```text
Main
 ↓
Service
 ↓
Repository
 ↓
PostgreSQL
```

Spring Boot版：

```text
Browser
 ↓
Controller
 ↓
Service
 ↓
Repository
 ↓
PostgreSQL
```

CLI版で作成した以下の部分は、Spring Boot版でも再利用しやすい。

- Model
- Service
- Repository
- PostgreSQLのテーブル設計
- Tier計算ロジック
- 検索・並び替えロジック

### Web版で追加したい機能

- Webブラウザ上でTier表をドラッグ＆ドロップ
- Tier表の共有URL発行
- ユーザーアカウント
- 複数人で同じTier表を評価
- 投票結果からコミュニティTierを生成
- Tier表を画像として出力
- JSON Import / Export
- 評価スコアのグラフ表示

---

## 15. 開発順序

```text
1. プロジェクト作成
2. PostgreSQL接続
3. Model作成
4. Repository作成
5. ボードCRUD
6. アイテムCRUD
7. 評価基準CRUD
8. 評価登録
9. Tier計算ロジック
10. Tier表表示
11. 検索・並び替え
12. 入力チェック
13. 例外処理
14. リファクタリング
15. GitHub整理
```

最初からすべて実装せず、CRUDを完成させたあとにTier自動生成や検索機能を追加する。
