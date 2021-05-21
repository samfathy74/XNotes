package com.example.xnotes.services;

import java.util.Random;

public class BackgroundTask {
    String[] arr = new String[]{
            "لا تتحدّى إنساناً ليس لديه شيء يخسره.", "الرجال كالأرقام، قيمتهم عند مواضعهم.",
            "البيت لا يبنى إلّا له عمد.. ولا عماد إذا لم ترس أوتاد.",
            "إنّ الغنيّ هو الغنيّ بنفسه.. ولو أنه عار المناكب حاف.", "الذي يولد ليزحف، لا يستطيع أن يطير.",
            "خذ من العيش ما كفى ومن الدهر ما صفى.", "الحكمة.. قوام العمل الصالح وأساس الخير العام.",
            "إذا قدّرك الله على من ظلمك فأجعل العفو عنه شكراً لله.",
            "من لم يعرف قدر النعم بوجدانها، عرفها بوجود فقدانها.", "لا تضيع هيبة الصمت برخيص الكلام.",
            "ليس المهم أن نكون أذكياء لكن المهم أن نستخدم ذكاءنا.", "الجاهل عدو نفسه فكيف يكون صديق غيره.",
            "قطرة المطر تحفر في الصخر.. ليس بالعنف ولكن بالتكرار.", "يا داخل الدار صلّي على النبيّ المختار.",
            "لو حرمني الزمن من لقياك فلن يحرمني من ذكراك.",
            "إنّ للحسنة نوراً في الوجه وضياء في القلب وأثراً في الخلق.", "من تدخل فيما لا يعنيه لقي ما لا يرضيه.",
            "تنام عينك والمظلوم منتبه يدعو عليك وعين الله لم تنم.",
            "إذا ضاق صدر المرء عن سر نفسه فصدر الذي يستودع السر أضيق.",
            "قيل للحكيم ما الصدق القبيح.. قال: ثناء المرء على نفس.", "أعزب دهر ولا أرمل شهر.",
            "الفرق بين الحكمة والجهالة هو كالفرق بين الأحياء والأموات.",
            "الحياة مليئة بالحجارة فلا تتعثر بها بل أجمعها وابن سلماً تصعد به للنجاح.",
            "خير لك أن تسأل مرتين من أن تخطئ مرة.",
            "بين الحب والوقت علاقة أبديّة فالحب يقتل الوقت بسرعة شديدة والوقت يقتل الحب ببطئ شديد.",
            "لا تبكي على أي علاقة في الحياة؛ لأن الذي تبكي من اجله لا يستحق دموعك والشخص الذي يستحق دموعك لم يدعك تبكي ابداً.",
            "من فقد الله فماذا وجد.. ومن وجد الله فماذا فقد.",
            "إذا قصرت يدك عن المكافأة فليصل لسانك بالشكر.",
            "في الإسلام خلق الله الإنسان ليكون خليفته في الأرض ، و في الحضارة الغربية ليس الإنسان إلا حيواناً متطوراً – مصطفى السباعي",
            "إن سلوك الانسان هو أكثر ما يدل على سياسته – محمد الغزالي",
            "ليس للحياة قيمة إلا إذا وجدنا فيها شيئا نناضل من أجله",
            "الانسان العاقل هو الذي يغلق فمه قبل ان يغلق الناس آذانهم",
            "جمال الوجه يجذب الانسان ، لكن جمال النفس يأسره",
            "لولا الحنين إلى جنة غابرة لما كان شعرُ ولا ذاكرة ولما كان للأبدية معنى العزاء – محمود درويش",
            "الحنين هو مسامرة الغائب للغائب ، والتفات البعيد إلى البعيد – محمود درويش",
            "أريد أن أتحرر من هذه الذاكرة المثقلة بالحنين والأوجاع",
            " ستجد أن الحياة لا تزال جديرة بالاهتمام، إذا كنت تبتسم.",
            " الحياة قصيرة ، لكن المصائب تجعلها طويلة",
            " الأفضل دائماً أن نتطلع للأمام بدلاً من النظر إلى الخلف.",
            "يجب أن يكون احساسك ايجابياً مهما كانت الظروف، ومهما كانت التحديات، ومهما كان المؤثر الخارجي .",
            "قد تتحمل الألم ساعات، لكن لا ترض باليأس لحظة.",
            "لا تيأس، فعادة ما يكون آخر مفتاح في مجموعة المفاتيح هو المناسب لفتح الباب.",
            " استعن بالله و لا تعجز.",
            "لا يأس مع الحياة ، و لا حياة مع اليأس.",
            "يصبح الانسان عجوزاً حين تحل الأعذار محل الأمل.",
            " لو شعرت ببعد الناس عنك أو بوحشة أو غربة، فتذكر قربك من الله .",
            "كل عسير إذا استعنت بالله فهو يسير",
            " الزهرة التي تتبع الشمس تفعل ذلك حتى في اليوم المليء بالغيوم.",
            "هناك من يتذمر لأن للورد شوكاً، و هناك من يتفاءل لأن فوق الشوك وردة.",
            " التفاؤل يمنحك هدوء الاعصاب في أحرج الأوقات .",
            " الأنانية هي هذا القانون المحدد للشعور، الذي على أساسه تكون الأشياء الأقرب هي الأكبر والأثقل، في حين أن كل تلك التي تبتعد تقل حجمًا وثقلاً",
            "يسمى شريرا كل من لا يعمل إلا لمصلحته الذاتية",
            "  حب الذات هو بالون تخرج منه عواصف الرياح عندما نحدث فيه ثقبا",
            "الانانية .. ليست أن يعيش الانسان كما يهوى ، و لكن أن يطالب الآخرين أن يعيشوا كما يريد هو ان يعيش",
            "كما أن الأنانية وحب الذات تشوش على العقل ، فإن الحب و متعته يجعل الخيال حاداً",
            "لا تدخل أصحاب المصالح الانانية في عملك لانهم يفسدونه",
            "من تلمس الظلم ومسه فالأولى به إلا يمارسه بدوره.",
            "  بين الظلم الظاهر والعدل الخفي خيط رفيع لا يراه إلا أهل القلوب.",
            " الظلم مؤذن بخراب العمران.",
            "برفقة العدالة تجتاز العالم، وبرفقة الظلم لا تعبر عتبة بيتك.",
            " إن مقأومة الظلم لا يحددها الإنتماء لدين أو عرق أو مذهب، بل يحددها طبيعة النفس البشرية التي تأبى الأستعباد وتسعى للحرية.",
            "لا تقف ابداً موقف المتفرج من الظلم أو الغباء.. القبر سيوفر متسع من الوقت للصمت.",
            " وأقتلُ داءٍ رؤيةُ المرءِ ظالماً.. يسيء ويتلى في المحافلِ حمدُهُ.",
            "إِياكم أن تظلموا أو تناصروا إلى الظلم إِن الظلمَ يردي ويهلك.",
            "منذ أن صار الاهتمام في زماننا إلكترونياً .. أصبحت جميع الحكايات تنتهي بِحذف ، و حظر ، وإخفاء ظهور",
            " أصبحت العلاقات تنتهي بالإهمال .. أكثر من إنتهائها بالمشاكل",
            "كرهت ان اهتم باحد حين اهتم اشعر بانهم يتغيرون وكلما زاد الإهتمام زاد التغير",
            "مع مرور الوقت ستدرك ان البعض لا يستحق كل ذلك الاهتمام الذي توليه لهم , ثم ستدرك ان الأشخاص الذين يستحقون كل الإهتمام قد هجرتهم منذ البداية",
            "بيت الظالم خراب.",
            " ظلم الأقارب أشد وقعاً من السيف.",
            "وَظلم ذوي القربى أشد مرارة على النفس من وقع الحسام المهند.",
            "من ظلم لا بد إن يظلم.",
            "وهل يصعب الاتفاق إلا على ذوى النفوس الخبيثة الطامعة التي تملؤها الأنانية ويغزوها الحقد ؟",
            "سيكون يومك مشابهاً للتعبير المرتسم على وجهك سواء، كان ذلك ابتساماً أو عبوساً .",
            "  المتشائم، أحمق يرى الضوء أمام عينيه، لكنه لا يصدق .",
            " انما المستقبل لأولئك الذين يؤمنون بجمال احلامهم",
            " غدا يوم افضل هكذا يقول لنا الامل،وهكذا يحثنا",
            "يرى المتشائم الصعوبة في كل فرصة ، أما المتفائل فيرى الفرصة في كل صعوبة .",
            "في القلب حزن لا يذهبه إلا السرور بمعرفة الله .",
            "لو شعرت ببعد الناس عنك أو بوحشة أو غربة، فتذكر قربك من الله .",
            "كل عسير اذا استعنت بالله فهو يسير .",
            "لا تتلكأ لتجمع الورود وتحتفظ بها لكن سر وستجد الورود على طول دربك يانعة لتنعم بها",
            " من الاحلام تنبثق الاشياء الثمينة الباقية التي لا يذوي جمالها",
            "يُمكن للإنسان أن يعيش بلا بَصر ولكنه لا يمكن أن يعيش بلا أمل.",
    };

    public String getRandomMessage() {
        Random rd = new Random();
        return arr[rd.nextInt(arr.length)];
    }

}





